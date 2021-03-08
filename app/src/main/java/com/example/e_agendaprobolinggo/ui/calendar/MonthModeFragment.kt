package com.example.e_agendaprobolinggo.ui.calendar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_agendaprobolinggo.R
import com.example.e_agendaprobolinggo.databinding.FragmentMonthModeBinding
import com.example.e_agendaprobolinggo.databinding.MonthCalendarDayBinding
import com.example.e_agendaprobolinggo.databinding.MonthCalendarHeaderBinding
import com.example.e_agendaprobolinggo.model.response.AgendaResponse
import com.example.e_agendaprobolinggo.model.response.DataAgenda
import com.example.e_agendaprobolinggo.ui.calendar.CalendarActivity.CALENDAR_DATA
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity
import com.example.e_agendaprobolinggo.utils.DateFormatter.localDateFormat
import com.example.e_agendaprobolinggo.utils.DateFormatter.yearMonthFormat
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class MonthModeFragment : Fragment() {

    private var _binding: FragmentMonthModeBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    private lateinit var agendaLocalDate: Map<LocalDate?, List<DataAgenda>>
    private lateinit var agendaCalendarAdapter: AgendaCalendarAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMonthModeBinding.inflate(inflater, container, false)

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
        binding.rvAgendaCalendarMonth.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = agendaCalendarAdapter
        }
        agendaCalendarAdapter.notifyDataSetChanged()
        val currentMonth = YearMonth.now()
        val daysOfWeek = daysOfWeekFromLocale()

        if (agendaResponse != null) {
            val dataAgenda = agendaResponse.data
            if (dataAgenda.size > 0) {
                agendaLocalDate = dataAgenda.groupBy { localDateFormat(it.rawDataAgenda.tanggal, it.rawDataAgenda.jam) }
                var startMonth = yearMonthFormat(dataAgenda.first().rawDataAgenda.tanggal)
                var endMonth = yearMonthFormat(dataAgenda.last().rawDataAgenda.tanggal)

                if (startMonth.isAfter(endMonth)) {
                    startMonth = endMonth.also { endMonth = startMonth }
                }

                binding.calendarViewMonth.setup(startMonth, endMonth, daysOfWeek.first())
                binding.calendarViewMonth.scrollToMonth(currentMonth)
            } else {
                binding.calendarViewMonth.setup(currentMonth, currentMonth, daysOfWeek.first())
                binding.calendarViewMonth.scrollToMonth(currentMonth)
            }
        } else {
            binding.calendarViewMonth.setup(currentMonth, currentMonth, daysOfWeek.first())
            binding.calendarViewMonth.scrollToMonth(currentMonth)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = MonthCalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@MonthModeFragment.binding
                            binding.calendarViewMonth.notifyDateChanged(day.date)
                            oldDate?.let { binding.calendarViewMonth.notifyDateChanged(it) }
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }
        binding.calendarViewMonth.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val tvDayText = container.binding.tvDayText
                val monthDayLayout = container.binding.monthDayLayout
                val containerIndicator = container.binding.containerIndicator
                tvDayText.text = day.date.dayOfMonth.toString()

                containerIndicator.removeAllViews()
                if (day.owner == DayOwner.THIS_MONTH) {
                    tvDayText.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                    monthDayLayout.setBackgroundResource(if (selectedDate == day.date) R.drawable.selected_day_background else 0)

                    val agendas = agendaLocalDate[day.date]
                    agendas?.forEach {

                        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val indicator = inflater.inflate(R.layout.agenda_calendar_indicator, null)
                        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                5)

                        layoutParams.setMargins(2, 0, 2, 3)
                        indicator.layoutParams = layoutParams
                        indicator.setBackgroundColor(Color.parseColor(it.statusBox))
                        containerIndicator.addView(indicator)
                    }
                } else {
                    tvDayText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    monthDayLayout.background = null
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val calendarDayHeaderLayout = MonthCalendarHeaderBinding.bind(view).headerLayout.root
        }
        binding.calendarViewMonth.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.calendarDayHeaderLayout.tag == null) {
                    container.calendarDayHeaderLayout.tag = month.yearMonth
                    month.yearMonth
                }
            }
        }

        binding.calendarViewMonth.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            binding.tvMonthYear.text = title

            selectedDate?.let {
                selectedDate = null
                binding.calendarViewMonth.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        binding.imgNextMonth.setOnClickListener {
            binding.calendarViewMonth.findFirstVisibleMonth()?.let {
                binding.calendarViewMonth.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        binding.imgPreviousMonth.setOnClickListener {
            binding.calendarViewMonth.findFirstVisibleMonth()?.let {
                binding.calendarViewMonth.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        agendaCalendarAdapter.agendas.clear()
        agendaCalendarAdapter.agendas.addAll(agendaLocalDate[date].orEmpty())
        agendaCalendarAdapter.notifyDataSetChanged()
    }

    private fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs

        }
        return daysOfWeek
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}