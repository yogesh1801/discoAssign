import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class Main {
    static ArrayList<Professor> professors = new ArrayList<>();
    static ArrayList<Course> courses = new ArrayList<>();
    public static void main(String[] args) {

        try(FileReader reader = new FileReader("./csv/professor.csv");
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){
            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("Name");
                Category category = Category.valueOf(csvRecord.get("Category"));
                double workload = Double.parseDouble(csvRecord.get("Workload"));
                String coursePreferencesStr = csvRecord.get("CoursePreferences");
                ArrayList<String> coursePreferences = parseCoursePreferences(coursePreferencesStr);
                professors.add(new Professor(name, category, workload, coursePreferences));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Professor> profX1 = new ArrayList<>();
        ArrayList<Professor> profX2 = new ArrayList<>();
        ArrayList<Professor> profX3 = new ArrayList<>();

        for (Professor professor : professors) {
            switch (professor.category) {
                case X1 -> profX1.add(professor);
                case X2 -> profX2.add(professor);
                case X3 -> profX3.add(professor);
                default -> System.out.println("Unknown category for professor: " + professor.name);
            }
        }

        courses.add(new Course("OOPs","CDC"));
        courses.add(new Course("LCS","CDC"));
        courses.add(new Course("CS103","CDC"));
        courses.add(new Course("CS104","CDC"));
        courses.add(new Course("CS105","CDC"));
        courses.add(new Course("CS106","CDC"));
        courses.add(new Course("CS107","CDC"));
        courses.add(new Course("CS108","CDC"));
        courses.add(new Course("CS109","CDC"));
        courses.add(new Course("CS110","CDC"));
        courses.add(new Course("HSS101","EL"));
        courses.add(new Course("HSS102","EL"));

        ArrayList<Course> courseCDC = new ArrayList<>();
        ArrayList<Course> courseEL = new ArrayList<>();

        for (Course courses : courses) {
            switch (courses.category) {
                case "CDC" -> courseCDC.add(courses);
                case "EL" -> courseEL.add(courses);
                default -> System.out.println("Unknown category for professor: " + courses.courseName);
            }
        }

        ArrayList<Professor> profX2new = new ArrayList<Professor>();
        ArrayList<Professor> profX3new = new ArrayList<Professor>();

        for (Course course : courseCDC) {
            AllocateToProfCat(profX1 , course);
            if(course.isCourseAvailable()){
                AllocateToProfCat(profX2,course);
                if(course.isCourseAvailable()){
                    AllocateToProfCat(profX3,course);
                    if(course.isCourseAvailable()){
                        course.deAllocateThisCourse();
                    }
                    else{
                        removeFromAllpreferences(course);
                    }
                }
                else{
                    removeFromAllpreferences(course);
                }
            }
            else{
                removeFromAllpreferences(course);
            }

        }

        for(Professor profs: profX2){
            if(profs.workLoadAllocated == 0.5){
                profX2new.add(profs);
            }
        }

        for (Course course : courseCDC) {
            if(course.isCourseAvailable()){
                AllocateToProfCat(profX2new,course);
                if(course.isCourseAvailable()){
                    AllocateToProfCat(profX3,course);
                    if(course.isCourseAvailable()){
                        course.deAllocateThisCourse();
                    }
                    else{
                        removeFromAllpreferences(course);
                    }
                }
                else{
                    removeFromAllpreferences(course);
                }
            }
            else{
                removeFromAllpreferences(course);
            }

        }

        for(Professor profs: profX3){
            if(profs.workLoadAllocated >= 0.5){
                profX3new.add(profs);
            }
        }

        for (Course course : courseCDC) {
            if(course.isCourseAvailable()){
                AllocateToProfCat(profX2new,course);
                if(course.isCourseAvailable()){
                    AllocateToProfCat(profX3new,course);
                    if(course.isCourseAvailable()){
                        course.deAllocateThisCourse();
                    }
                    else{
                        removeFromAllpreferences(course);
                    }
                }
                else{
                    removeFromAllpreferences(course);
                }
            }
            else{
                removeFromAllpreferences(course);
            }

        }


        for(Professor professor : profX1) {
            System.out.println(professor);
        }

        for(Professor professor : profX2) {
            System.out.println(professor);
        }

        for(Professor professor : profX3) {
            System.out.println(professor);
        }
    }

    public static void removeFromAllpreferences(Course course){
            if(!course.isCourseAvailable()){
                for(Professor prof: professors){
                    prof.preferenceList.remove(course.courseName);
                }
            }
    }

    public static int sortPreferencesArray(ArrayList<Integer> array){
        for(int i = 0; i < array.size(); i++){
            int count = 0;
            if(i != array.size()-1) {
                for (int j = i + 1; j < array.size(); j++) {
                    if (array.get(j) > array.get(i)) {
                        count++;
                    }
                    if (count == (array.size() - i - 1)) {
                        return array.get(i);
                    } else {
                        continue;
                    }
                }
            }
            else{
                return array.get(i);
            }
        }
        return -1;
    }

    public static void AllocateToProfCat(ArrayList<Professor> list , Course course){
        if(course.courseAvailable == 0.5) {
            int temp = 100000;
            ArrayList<Integer> preferencesIndex = new ArrayList<>();
            ArrayList<Professor> profIndex = new ArrayList<>();
            for (Professor prof : list) {
                temp = prof.getPreferenceIndex(course.courseName);
                preferencesIndex.add(temp);
            }

            for (int i = 0; i < preferencesIndex.size(); i++) {
                if (preferencesIndex.get(i) == sortPreferencesArray(preferencesIndex)) {
                    profIndex.add(list.get(i));
                } else {
                    continue;
                }
            }

            for (Professor newProf : profIndex) {
                if (newProf.isCourseAllocable(course) && course.isCourseAvailable()) {
                    newProf.allocateCourse(course);
                    break;
                }
            }
        }
        else{
            for (Professor prof : list) {
                if (prof.isCourseAllocable(course) && prof.getPreferenceIndex(course.courseName) == 0 && course.isCourseAvailable()) {
                    prof.allocateCourse(course);
                }
            }

        }
    }

    public static ArrayList<String> parseCoursePreferences(String coursePreferencesStr) {
        ArrayList<String> coursePreferences = new ArrayList<>();
        String[] courses = coursePreferencesStr.split(",");
        for (String course : courses) {
            coursePreferences.add(course.trim());
        }
        return coursePreferences;
    }
}