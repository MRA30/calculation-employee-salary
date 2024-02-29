package com.mra;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat formatterRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        List<String> employeeLevelList = Arrays.asList(
                "Staff",
                "Supervisor",
                "Manager"
        );



        //Nama
        System.out.println("Siapa nama karyawan ini?");
        String nama = scanner.nextLine();

        //Tanggal Karyawan Mulai Bekerja
        Date startWork = null;
        while (startWork == null){
            try {
                System.out.println("Tanggal berapa karyawan mulai bekerja? (format : dd/MM/yyyy)");
                String inputStart = scanner.nextLine();
                Calculation calculation = new Calculation();
                String inputStartChecked = calculation.checkingStartDate(inputStart);
                startWork = formatTanggal.parse(inputStartChecked);
            }catch (Exception exception){
                System.out.println("Format Tanggal Salah, coba lagi.");
            }
        }
        System.out.println(startWork);

        //Tanggal Karyawan Di PHK
        Date endWork = null;
        while (endWork == null) {
            try {
                System.out.print("Tanggal berapa karyawan ini di PHK secara baik-baik? (dd/MM/yyyy): ");
                String input = scanner.nextLine();
                endWork = formatTanggal.parse(input);
            } catch (Exception exception) {
                System.out.println("Format tanggal salah, coba lagi.");
            }
        }

        Integer firstSalary = null;
        while (firstSalary == null) {
            try {
                System.out.print("Berapa gaji karyawan pertama kali dia bekerja?");
                String input = scanner.nextLine();
                if (input.matches("[0-9]+") && !input.contains(".")) {
                    int jumlahUang = Integer.parseInt(input);
                    System.out.println("Jumlah uang yang dimasukkan: " + jumlahUang);
                } else {
                    throw new Exception("input harus bilangan bulat, tidak ada sen di dalam rupiah");
                }
                firstSalary = Integer.parseInt(input);
            }catch (Exception exception) {
                System.out.println("Input bukan angka.");
            }
        }

        String employeeLevel = null;
        while (employeeLevel == null) {
            System.out.print("Apa level karyawan ini?");
            String input = scanner.nextLine();
            if (!employeeLevelList.contains(input)) {
                throw new Exception("level karyawan harus " + String.join(" atau ", employeeLevelList));
            }
            employeeLevel = input;
        }

        System.out.println(nama + " (" + employeeLevel + ")");
        Calculation calculation = new Calculation();
        List<Integer> salaryIncrease = calculation.calculationSalaryIncrease(firstSalary, employeeLevel, startWork, endWork);
        Integer yearWorking = 0;
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