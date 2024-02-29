package com.mra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Calculation {

    List<String> employeeLevelList = Arrays.asList(
            "Staff",
            "Supervisor",
            "Manager"
    );

    private Integer salaryIncreaseStaff = 10;

    private Integer salaryIncreaseSupervisor = 8;

    private Integer salaryIncreaseManager = 12;


    public List<Integer> calculationSalaryIncrease(Integer salary, String employeeLevel, Date startDate, Date endDate) {
        Integer yearDifference = calculateYearDifference(startDate, endDate);
        List<Integer> result = new ArrayList<>();
        Integer newSalary;
        for (int i = 0; i < yearDifference; i++) {
            if (result.isEmpty()) {
                result.add(salary);
            }else {
                newSalary = calculateIncreaseSalary(result.get(result.size() - 1), employeeLevel);
                result.add(newSalary);
            }
        }
        return result;
    }

    public Integer calculateYearDifference(Date startDate, Date endDate) {

        // Mengonversi string menjadi objek LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Menghitung selisih tahun antara dua tanggal
        long selisihBulan = ChronoUnit.MONTHS.between(startLocalDate.withDayOfMonth(1), endLocalDate.withDayOfMonth(1));

        // Menghitung selisih tahun antara dua tanggal
        long selisihTahun = selisihBulan / 12;

        // Jika tanggal akhir lebih besar dari tanggal awal, tambahkan satu tahun
        if (endLocalDate.isAfter(startLocalDate.plusMonths(selisihTahun * 12))) {
            selisihTahun++;
        }
        return (int) selisihTahun;
    }

    public Integer calculateIncreaseSalary(Integer salary, String employeeLevel) {
        int newSalary = 0;
        if (employeeLevel.equals(employeeLevelList.get(0))) {
            newSalary = salary + Math.round(salary * ((float) salaryIncreaseStaff / 100));
        }else if (employeeLevel.equals(employeeLevelList.get(1))) {
            newSalary = salary + Math.round(salary * ((float) salaryIncreaseSupervisor / 100));
        }else if (employeeLevel.equals(employeeLevelList.get(2))) {
            newSalary = salary + Math.round(salary * ((float) salaryIncreaseManager / 100));
        }
        return newSalary;
    }

    public String checkingStartDate(String date) {
        String[] tanggal = date.split("/");

        int day = Integer.parseInt(tanggal[0]);
        int month = Integer.parseInt(tanggal[1]);
        int year = Integer.parseInt(tanggal[2]);

        if (day == 29 && month == 2) {
            return "28" + "/" + month + "/" + year;
        }
        return date;
    }

}
