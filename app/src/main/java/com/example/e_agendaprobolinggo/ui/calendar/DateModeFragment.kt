package com.example.e_agendaprobolinggo.ui.calendar

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_agendaprobolinggo.R
import com.example.e_agendaprobolinggo.databinding.DateCalendarDayBinding
import com.example.e_agendaprobolinggo.databinding.FragmentDateModeBinding
import com.example.e_agendaprobolinggo.model.response.AgendaResponse
import com.example.e_agendaprobolinggo.model.response.DataAgenda
import com.example.e_agendaprobolinggo.ui.calendar.CalendarActivity.CALENDAR_DATA
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity
import com.example.e_agendaprobolinggo.utils.DateFormatter
import com.example.e_agendaprobolinggo.utils.DateFormatter.yearMonthFormat
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DateModeFragment : Fragment() {

    private var _binding: FragmentDateModeBinding? = null
    private val binding get() = _binding!!

    private var selectedDate = LocalDate.now()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    private lateinit var agendaCalendarAdapter: AgendaCalendarAdapter
    private lateinit var agendaLocalDate: Map<LocalDate?, List<DataAgenda>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDateModeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agendaResponse: AgendaResponse? = requireArguments().getParcelable(CALENDAR_DATA)

        agendaCalendarAdapter = AgendaCalendarAdapter()
        agendaCalendarAdapter.onItemClick = { code ->
            Intent(activity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.CODE, code)
                startActivity(this)
            }
        }
        binding.rvAgendaCalendarDate.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = agendaCalendarAdapter
        }
        agendaCalendarAdapter.notifyDataSetChanged()

        val currentMonth = YearMonth.now()

        if (agendaResponse != null) {
            val dataAgenda = agendaResponse.data
            if (dataAgenda.size > 0) {
                agendaLocalDate = dataAgenda.groupBy { DateFormatter.localDateFormat(it.rawDataAgenda.tanggal, it.rawDataAgenda.jam) }
                var startMonth = yearMonthFormat(dataAgenda.first().rawDataAgenda.tanggal)
                var endMonth = yearMonthFormat(dataAgenda.last().rawDataAgenda.tanggal)
                if (startMonth.isAfter(endMonth)) {
                    startMonth = endMonth.also { endMonth = startMonth }
                }

                binding.calendarViewDate.setup(startMonth, endMonth, DayOfWeek.values().random())
                binding.calendarViewDate.scrollToDate(LocalDate.now())
            } else {
                binding.calendarViewDate.setup(currentMonth, currentMonth, DayOfWeek.values().random())
                binding.calendarViewDate.scrollToMonth(currentMonth)
            }
        } else {
            binding.calendarViewDate.setup(currentMonth, currentMonth, DayOfWeek.values().random())
            binding.calendarViewDate.scrollToMonth(currentMonth)
        }

        val dm = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(dm)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(dm)
        }
        binding.calendarViewDate.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = DateCalendarDayBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = binding.calendarViewDate.findFirstVisibleDay()
                    val lastDay = binding.calendarViewDate.findLastVisibleDay()
                    if (firstDay == day) {
                        binding.calendarViewDate.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {
                        binding.calendarViewDate.smoothScrollToDate(day.date.minusDays(4))
                    }

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.calendarViewDate.notifyDateChanged(day.date)
                        oldDate?.let { binding.calendarViewDate.notifyDateChanged(it) }
                        updateAdapterForDate(day.date)
                    }
                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                bind.tvDateText.text = dateFormatter.format(day.date)
                bind.tvDayText.text = dayFormatter.format(day.date)
                bind.tvMonthText.text = monthFormatter.format(day.date)

                agendaLocalDate.keys.forEach {
                    if (it == day.date) {
                        bind.tvDateText.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_orange))
                        bind.tvDayText.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_orange))
                        bind.tvMonthText.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_orange))
                    }
                }

                bind.selectedView.visibility = if (day.date == selectedDate) View.VISIBLE else View.GONE

                if (selectedDate == day.date) {
                    updateAdapterForDate(day.date)
                }

            }
        }

        binding.calendarViewDate.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        agendaCalendarAdapter.agendas.clear()
        if (!agendaLocalDate[date].isNullOrEmpty()) {
            binding.tvEmptyData.visibility = View.GONE
            agendaCalendarAdapter.agendas.addAll(agendaLocalDate[date].orEmpty())
        } else {
            binding.tvEmptyData.visibility = View.VISIBLE
        }
        agendaCalendarAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}