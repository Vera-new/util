//java version "21.0.2" 2024-01-16 LTS
//Java(TM) SE Runtime Environment (build 21.0.2+13-LTS-58)
//Java HotSpot(TM) 64-Bit Server VM (build 21.0.2+13-LTS-58, mixed mode, sharing)

package com.company.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static boolean isInteger(String string) {
        long intValue;
        if (string == null || string.isEmpty()) {
            return false;
        }
        try {
            intValue = Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static boolean isDouble(String string) {
        double doublValue;
        if (string == null || string.isEmpty() || isInteger(string)) {
            return false;
        }
        try {
            doublValue = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
    private static void moveFile(String src, String dest ) {
        Path result = null;
        try {
            result =  Files.move(Paths.get(src), Paths.get(dest));
        } catch (IOException e) {
            System.out.println("Неверно указан путь файла: " + e.getMessage());
        }
        if(result != null) {
            System.out.println("Файл успешно перемещён.");
        }else{
            System.out.println("Файл не перемещён.");
        }
    }

    public static void main(String[] args) {
        int countInt = 0;
        int countFl = 0;
        int countStr = 0;
        long minInt = 9223372036854775807L;
        long maxInt = -9223372036854775808L;
        double minFl = 1.7*Math.pow(10,308);
        double maxFl = 1.7*Math.pow(10,(-308));
        long sumInt = 0;
        double sumFl=0;
        int i = 0;
        int lenMax = 0;
        int lenMin = 2147483647;
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.out.print("Вам необходимо ввести путь к файлу");
            return;
        }
        else {
            try {
                for (String str : args) {
                        File file = new File(args[i]);
                        i++;
                        FileReader fr = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fr);
                        String line = reader.readLine();
                        while (line != null) {
                            if (isInteger(line)) {
                                countInt +=1;
                                sumInt+=Long.parseLong(line);
                                if (minInt > Long.parseLong(line)){
                                    minInt = Long.parseLong(line);
                                }
                                if (maxInt < Long.parseLong(line)){
                                    maxInt = Long.parseLong(line);
                                }
                                File dir = new File(System.getProperty("user.dir"), "integers.txt");
                                try (FileWriter writer = new FileWriter(dir, true)) {
                                    writer.write(line);
                                    writer.append('\n');
                                    writer.flush();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            } else if (isDouble(line)) {
                                try (FileWriter writer = new FileWriter("floats.txt", true)) {
                                    countFl +=1;
                                    sumFl+=Double.parseDouble(line);
                                    if (minFl > Double.parseDouble(line)){
                                        minFl = Double.parseDouble(line);
                                    }
                                    if (maxFl < Double.parseDouble(line)){
                                        maxFl = Double.parseDouble(line);
                                    }
                                    writer.write(line);
                                    writer.append('\n');
                                    writer.flush();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            } else {
                                try (FileWriter writer = new FileWriter("strings.txt", true)) {
                                    countStr +=1;
                                    if(lenMax<line.length()) {
                                        lenMax = line.length();
                                    }
                                    if(lenMin>line.length()) {
                                        lenMin = line.length();
                                    }
                                    writer.write(line);
                                    writer.append('\n');
                                    writer.flush();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                            line = reader.readLine();
                        }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Вы ввели неверный путь. Повторите действие");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       String option = "";
          do {
              System.out.println("---------------------------------------------------");
              System.out.println("Введите -o - чтобы указать путь, куда сохранить файл с результатом (путь, потом название файла) \n " +
                      "\tНапример: -o C:/Users/User/Desktop/MyFolder -p integers (floats или strings)");
              System.out.println("Введите -a - чтобы добавить данные в какой либо файл \n " +
                      "\tНапример: -а new data");
              System.out.println("Введите -s - чтобы вывести краткую информацию о файлах");
              System.out.println("Введите -f - чтобы вывести полную информацию о файлах");
              System.out.println("Введите 0 - чтобы выйти из программы");
              System.out.println("---------------------------------------------------");
              Scanner in = new Scanner(System.in);
              option = in.next();

              switch (option) {
                  case "-o":
                      int time = 0;
                      String pathRez = in.next().trim();
                      String option2 = in.next().trim();
                      String pref = in.next().trim();
                      if (option2.equals("-p")) {
                              if (pref.equals("integers")) {
                                  String dirIn = System.getProperty("user.dir")+ "\\integers.txt";
                                  pathRez = pathRez+"\\"+pref+".txt";
                                  System.out.println("переносим из: "+dirIn);
                                  System.out.println("переносим в: "+pathRez);
                                  moveFile(dirIn, pathRez);
                              }
                              else if (pref.equals("floats") || pref.equals("floats.txt")) {
                                  String dirFl = System.getProperty("user.dir")+ "\\floats.txt";
                                  pathRez = pathRez+"\\"+pref+".txt";
                                  System.out.println("переносим из: "+dirFl);
                                  System.out.println("переносим в: "+pathRez);
                                  moveFile(dirFl, pathRez);
                              }
                              else if (pref.equals("strings") || pref.equals("strings.txt")) {
                                  String dirSt = System.getProperty("user.dir")+ "\\strings.txt";
                                  pathRez = pathRez+"\\"+pref+".txt";
                                  System.out.println("переносим из: "+dirSt);
                                  System.out.println("переносим в: "+pathRez);
                                  moveFile(dirSt, pathRez);
                              }
                              else {
                                  System.out.println("Недопустимое имя файла");
                                  System.out.println("Введите другую опцию или введите 0 для выхода");
                              }
                      }
                      else System.out.println("Неверная команда");
                      break;
                  case "-a":
                      try {
                          String str = in.nextLine().trim();
                          if (isInteger(str)) {
                              File dirInt = new File(System.getProperty("user.dir"), "integers.txt");
                              try (FileWriter writer = new FileWriter(dirInt, true)) {
                                  countInt +=1;
                                  sumInt+=Long.parseLong(str);
                                  writer.write(str);
                                  writer.append('\n');
                                  writer.flush();
                                  System.out.println("Данные добавлены");
                                  if (minInt > Long.parseLong(str)){
                                      minInt = Long.parseLong(str);
                                  }
                                  if (maxInt < Long.parseLong(str)){
                                      maxInt = Long.parseLong(str);
                                  }
                              } catch (IOException ex) {
                                  System.out.println(ex.getMessage());
                              }
                          } else if (isDouble(str)) {
                              File dirFl = new File(System.getProperty("user.dir"), "floats.txt");
                              try (FileWriter writer = new FileWriter(dirFl, true)) {
                                  countFl +=1;
                                  sumFl+=Double.parseDouble(str);
                                  writer.write(str);
                                  writer.append('\n');
                                  writer.flush();
                                  System.out.println("Данные добавлены");
                                  if (minFl > Double.parseDouble(str)){
                                      minFl = Double.parseDouble(str);
                                  }
                                  if (maxFl < Double.parseDouble(str)){
                                      maxFl = Double.parseDouble(str);
                                  }
                              } catch (IOException ex) {
                                  System.out.println(ex.getMessage());
                              }
                          } else {
                              try (FileWriter writer = new FileWriter("strings.txt", true)) {
                                  countStr +=1;
                                  writer.write(str);
                                  writer.append('\n');
                                  writer.flush();
                                  System.out.println("Данные добавлены");
                                  if(lenMax<str.length()) {
                                      lenMax = str.length();
                                  }
                                  if(lenMin>str.length()) {
                                      lenMin = str.length();
                                  }
                              } catch (IOException ex) {
                                  System.out.println(ex.getMessage());
                              }
                          }

                      } catch (NumberFormatException e) {
                          System.out.println("Данные не распознаны. Проверьте данные и повторите ввод");
                      }

                      break;
                  case "-s":
                      if (countInt == 0 && countFl == 0 && countStr == 0) System.out.println("Нет файлов для анализа");
                      else if (countInt == 0 && countFl == 0 ) System.out.println("В файле strings.txt " + countStr + " строк");
                      else if (countInt == 0 && countStr == 0 ) System.out.println("В файле floats.txt " + countFl + " строк");
                      else if (countFl == 0 && countStr == 0 ) System.out.println("В файле integers.txt " + countInt + " строк");
                      else if (countInt == 0) {
                          System.out.println("В файле floats.txt " + countFl + " строк");
                          System.out.println("В файле strings.txt " + countStr + " строк");
                      }
                      else if (countFl == 0) {
                          System.out.println("В файле integers.txt " + countInt + " строк");
                          System.out.println("В файле strings.txt " + countStr + " строк");
                      }
                      else if (countStr == 0) {
                          System.out.println("В файле integers.txt " + countInt + " строк");
                          System.out.println("В файле floats.txt " + countFl + " строк");
                      }
                      else {
                          System.out.println("В файле integers.txt " + countInt + " строк");
                          System.out.println("В файле floats.txt " + countFl + " строк");
                          System.out.println("В файле strings.txt " + countStr + " строк");
                      }
                      break;
                  case "-f":
                      if (countInt == 0 && countFl == 0 && countStr == 0) System.out.println("Нет файлов для анализа");
                      else if (countInt == 0 && countFl == 0 ) {
                          System.out.println("В файле strings.txt: ");
                          System.out.println("\t" + countStr + " строк");
                          System.out.println("\tМаксимальная длина слова: " + lenMax);
                          System.out.println("\tМинимальная длина слова: " + lenMin);
                      }

                      else if (countInt == 0 && countStr == 0 ) {
                          System.out.println("В файле floats.txt: ");
                          System.out.println("\t" + countFl + " строк");
                          System.out.println("\tМаксимальное число: " + maxFl);
                          System.out.println("\tМинимальное число: " + minFl);
                          System.out.println("\tСумма всех чисел: " + sumFl);
                          System.out.println("\tСреднее число: " + sumFl/countFl);
                          System.out.println(" ");
                        }
                      else if (countFl == 0 && countStr == 0 ) {
                            System.out.println("Файл integers.txt: \n\t" + countInt + " строк\n\tМаксимальное число: " + maxInt +"\n\tМинимальное число: " +
                              minInt +"\n\tСумма всех чисел: " + sumInt +"\n\tСреднее число: " + (double)sumInt/countInt);
                      System.out.println(" ");
                      }
                      else if (countInt == 0) {
                          System.out.println("В файле floats.txt: ");
                          System.out.println("\t" + countFl + " строк");
                          System.out.println("\tМаксимальное число: " + maxFl);
                          System.out.println("\tМинимальное число: " + minFl);
                          System.out.println("\tСумма всех чисел: " + sumFl);
                          System.out.println("\tСреднее число: " + sumFl/countFl);
                          System.out.println(" ");
                          System.out.println("В файле strings.txt: ");
                          System.out.println("\t" + countStr + " строк");
                          System.out.println("\tМаксимальная длина слова: " + lenMax);
                          System.out.println("\tМинимальная длина слова: " + lenMin);
                      }
                      else if (countFl == 0) {
                          System.out.println("Файл integers.txt: \n\t" + countInt + " строк\n\tМаксимальное число: " + maxInt +"\n\tМинимальное число: " +
                              minInt +"\n\tСумма всех чисел: " + sumInt +"\n\tСреднее число: " + (double)sumInt/countInt);System.out.println(" ");
                          System.out.println("В файле strings.txt: ");
                          System.out.println("\t" + countStr + " строк");
                          System.out.println("\tМаксимальная длина слова: " + lenMax);
                          System.out.println("\tМинимальная длина слова: " + lenMin);
                      }
                      else if (countStr == 0) {
                      System.out.println("Файл integers.txt: \n\t" + countInt + " строк\n\tМаксимальное число: " + maxInt +"\n\tМинимальное число: " +
                              minInt +"\n\tСумма всех чисел: " + sumInt +"\n\tСреднее число: " + (double)sumInt/countInt);
                      System.out.println(" ");
                          System.out.println("В файле floats.txt: ");
                          System.out.println("\t" + countFl + " строк");
                          System.out.println("\tМаксимальное число: " + maxFl);
                          System.out.println("\tМинимальное число: " + minFl);
                          System.out.println("\tСумма всех чисел: " + sumFl);
                          System.out.println("\tСреднее число: " + sumFl/countFl);
                          System.out.println(" ");
                      }
                      else {
                      System.out.println("Файл integers.txt: \n\t" + countInt + " строк\n\tМаксимальное число: " + maxInt +"\n\tМинимальное число: " +
                              minInt +"\n\tСумма всех чисел: " + sumInt +"\n\tСреднее число: " + (double)sumInt/countInt);
                      System.out.println(" ");
                          System.out.println("В файле floats.txt: ");
                          System.out.println("\t" + countFl + " строк");
                          System.out.println("\tМаксимальное число: " + maxFl);
                          System.out.println("\tМинимальное число: " + minFl);
                          System.out.println("\tСумма всех чисел: " + sumFl);
                          System.out.println("\tСреднее число: " + sumFl/countFl);
                          System.out.println(" ");
                          System.out.println("В файле strings.txt: ");
                          System.out.println("\t" + countStr + " строк");
                          System.out.println("\tМаксимальная длина слова: " + lenMax);
                          System.out.println("\tМинимальная длина слова: " + lenMin);
                      }
                      break;
                  case "0":
                      java.lang.System.exit(0);
                  default:
                      System.out.println("Неверная команда");
              }
          }
       while (option != "0");
    }
}