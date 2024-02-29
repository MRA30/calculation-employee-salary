package com.mra;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat formatterRupiah = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        formatterRupiah.applyPattern("#,###");

        List<String> employeeLevelList = Arrays.asList(
                "Staff",
                "Supervisor",
                "Manager"
        );

        int salaryIncreaseStaff = 10;

        int salaryIncreaseSupervisor = 8;

        int salaryIncreaseManager = 12;


        //Nama
        System.out.println("Siapa nama karyawan ini?");
        String nama = scanner.nextLine();

        //Tanggal Karyawan Mulai Bekerja
        Date startWork = null;
        while (startWork == null) {
            try {
                System.out.println("Tanggal berapa karyawan mulai bekerja? (format : dd/MM/yyyy)");
                String inputStart = scanner.nextLine();
                String[] tanggal = inputStart.split("/");

                int day = Integer.parseInt(tanggal[0]);
                int month = Integer.parseInt(tanggal[1]);
                int year = Integer.parseInt(tanggal[2]);

                if (day == 29 && month == 2) {
                    inputStart = "28" + "/" + month + "/" + year;
                }
                startWork = formatTanggal.parse(inputStart);
            } catch (Exception exception) {
                System.out.println("Format Tanggal Salah, coba lagi.");
            }
        }
        //Tanggal Karyawan Di PHK
        Date endWork = null;
        while (endWork == null) {
            try {
                System.out.println("Tanggal berapa karyawan ini di PHK secara baik-baik? (dd/MM/yyyy): ");
                String input = scanner.nextLine();
                endWork = formatTanggal.parse(input);
            } catch (Exception exception) {
                System.out.println("Format tanggal salah, coba lagi.");
            }
        }

        Integer firstSalary = null;
        while (firstSalary == null) {
            try {
                System.out.println("Berapa gaji karyawan pertama kali dia bekerja?");
                String input = scanner.nextLine();
                if (!input.matches("[0-9]+") && input.contains(".")) {
                    throw new Exception("input harus bilangan bulat, tidak ada sen di dalam rupiah");
                }
                firstSalary = Integer.parseInt(input);
            } catch (Exception exception) {
                System.out.println("Input bukan angka.");
            }
        }

        String employeeLevel = null;
        while (employeeLevel == null) {
            System.out.println("Apa level karyawan ini?");
            String input = scanner.nextLine();
            if (!employeeLevelList.contains(input)) {
                throw new Exception("level karyawan harus " + String.join(" atau ", employeeLevelList));
            }
            employeeLevel = input;
        }

        System.out.println(nama + " (" + employeeLevel + ")");
        List<Integer> salaryIncrease = new ArrayList<>();
        LocalDate startLocalDate = startWork.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endWork.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Menghitung selisih tahun antara dua tanggal
        long selisihBulan = ChronoUnit.MONTHS.between(startLocalDate.withDayOfMonth(1), endLocalDate.withDayOfMonth(1));

        // Menghitung selisih tahun antara dua tanggal
        long selisihTahun = selisihBulan / 12;

        // Jika tanggal akhir lebih besar dari tanggal awal, tambahkan satu tahun
        if (endLocalDate.isAfter(startLocalDate.plusMonths(selisihTahun * 12))) {
            selisihTahun++;
        }
        Integer newSalary = null;
        for (int i = 0; i < selisihTahun; i++) {
            if (salaryIncrease.isEmpty()) {
                salaryIncrease.add(firstSalary);
            } else {
                if (employeeLevel.equals(employeeLevelList.get(0))) {
                    newSalary = salaryIncrease.getLast() + Math.round(salaryIncrease.getLast() * ((float) salaryIncreaseStaff / 100));
                } else if (employeeLevel.equals(employeeLevelList.get(1))) {
                    newSalary = salaryIncrease.getLast() + Math.round(salaryIncrease.getLast() * ((float) salaryIncreaseSupervisor / 100));
                } else if (employeeLevel.equals(employeeLevelList.get(2))) {
                    newSalary = salaryIncrease.getLast() + Math.round(salaryIncrease.getLast() * ((float) salaryIncreaseManager / 100));
                }
                salaryIncrease.add(newSalary);
            }
        }
        int yearWorking = 0;
        Integer lastSalary = salaryIncrease.getLast();
        for (Integer salary : salaryIncrease) {
            String startDate = formatTanggal.format(startWork);
            String[] tanggal = startDate.split("/");

            int day = Integer.parseInt(tanggal[0]);
            int month = Integer.parseInt(tanggal[1]);
            int year = Integer.parseInt(tanggal[2]) + yearWorking;
            String nowDate = day + "/" + month + "/" + year;
            System.out.println("Gaji karyawan sejak " + nowDate + " adalah Rp " + formatterRupiah.format(salary) + ",-");
            yearWorking = yearWorking + 1;
        }
        System.out.println("Di PHK di tahun ke " + salaryIncrease.size());
        System.out.println("Dengan pesangon sebesar : " + "Rp " + formatterRupiah.format((long) lastSalary * salaryIncrease.size()) + ",-");
    }
}