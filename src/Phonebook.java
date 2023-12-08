
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// Реализуйте структуру телефонной книги с помощью HashMap.
   //      Программа также должна учитывать, что в во входной структуре будут повторяющиеся имена с разными телефонами,
     //   их необходимо считать, как одного человека с разными телефонами. Вывод должен быть отсортирован по убыванию числа телефонов.

//Класс Phonebook реализует базу данный «Телефонный справочник»
//БД постоена на основе контейнера HashMap<String, String>
//в качестве ключа используется номер телефона в виде строки, а в качестве значения - фамилия
//класс включает в себя метод main который запускает работу с БД
//также включены методы
//addPhoneBook - добавляет запись по заданным номеру телефона и фамилии
//delPhoneBook - удаляет запись по номеру телефона
//savePhoneBook - сохраняет БД в текстовом файле phone.txt
//loadPhoneBook - загружает БД из текстового файла phone.txt
//PrintPhonebook - выводит на экран все записи БД
//FindSurname - производит поиск фамилии по номеру телефона
//FindNumberPhone - производит поиск списка номеров по фамилии

public class Phonebook {
    private static HashMap<String, String> phonebook = new HashMap<String, String>();

    //addPhoneBook - добавляет запись по заданным номеру телефона и фамилии
    private static void addPhoneBook(String phone, String name) {
        phonebook.put(phone, name);
    }

    //delPhoneBook - удаляет запись по номеру телефона
    private static void delPhoneBook(String phone) {
        phonebook.remove(phone);
    }

    //savePhoneBook - сохраняет БД в текстовом файле phone.txt
    private static void savePhoneBook() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("phone.txt")));
        for (Map.Entry<String, String> k : phonebook.entrySet()) {
            writer.write(k.getKey() + " " + k.getValue() + System.lineSeparator());
        }
        writer.close();
    }

    //loadPhoneBook - загружает БД из текстового файла phone.txt
    //производит проверку на наличие файла
    public static void loadPhoneBook() throws IOException {
        File file = new File("phone.txt");
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(new File("phone.txt")));
            String act;
            while ((act = reader.readLine()) != null) {
                String[] dat = act.split(" ");
                phonebook.put(dat[0], dat[1]);
            }
            reader.close();
        }
    }

    //PrintPhonebook - выводит на екран все записи БД
    public static void PrintPhonebook() {
        System.out.println("Телефонный справочник: ");
        for (Map.Entry<String, String> k : phonebook.entrySet()) {
            System.out.println(k.getValue() + ": " + k.getKey());
        }
    }

    //FindSurname - производит поиск фамилии по номеру телефона заданому в качестве аргумента
    //возвращает строку
    public static String FindSurname(String number) {
        String result = phonebook.get(number);
        if (result == null) return "абонент с таким номером не найден";
        return result;
    }

    //FindNumberPhone - производит поиск списка номеров по фамилии заданой в качестве аргумента
    //возвращает массив строк
    public static String[] FindNumberPhone(String surname) {
        List<String> result = new ArrayList<String>();
        for (Map.Entry entry : phonebook.entrySet()) {
            if (surname.equalsIgnoreCase((String) entry.getValue())) {
                result.add((String) entry.getKey());
            }
        }
        if (result.size() == 0) result.add("абонент с такой фамилией не найден");
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException {
        //переменная описывает вызываемое действие
        String act;

        //загрузка БД
        loadPhoneBook();
        //вывод записей на экран
        PrintPhonebook();

        //вывод на екран описания возможных действий с указанием команд
        System.out.println("выбор действия: (add)добавить данные, (del)удалить данные, (num) найти номера по фамилии, (sur)найти фамилию, " +
                "(save)сохранить, (exit)выход");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        act = bf.readLine();
        while (!act.equals("exit")) {
            //добавление записи
            if (act.equals("add")) {
                System.out.println("Введите фамилию:");
                String name = bf.readLine();
                System.out.println("Введите телефон:");
                String phone = bf.readLine();
                addPhoneBook(phone, name);
            } else {
                //удаление записи
                if (act.equals("del")) {
                    System.out.println("Введите телефон:");
                    String phone = bf.readLine();
                    delPhoneBook(phone);
                } else {
                    //поиск номеров по фамилии
                    if (act.equals("num")) {
                        System.out.println("Введите фамилию:");
                        String surname = bf.readLine();
                        String[] numbers = FindNumberPhone(surname);
                        for (String number : numbers) {
                            System.out.println(number);
                        }
                    } else {
                        //поиск фамилии по номеру
                        if (act.equals("sur")) {
                            System.out.println("Введите номер:");
                            String number = bf.readLine();
                            System.out.println(FindSurname(number));
                        } else {
                            //сохранение БД в файл
                            if (act.equals("save")) {
                                savePhoneBook();
                            }
                        }
                    }
                }
            }
            //запрос на следующее действие
            System.out.println("выбор действия: (add)добавить данные, (del)удалить данные, (num) найти номера по фамилии, (sur)найти фамилию, (save)сохранить, (exit)выход");
            act = bf.readLine();
        }
    }
}