import java.util.ArrayList;

class Course {
    public String category;
    public String courseName;
    public int courseWeight;
    public double courseAvailable;

    public boolean isCourseAvailable() {
        return this.courseAvailable > 0;
    }

    public ArrayList<Professor> allocatedToProf = new ArrayList<>();

    public boolean containsCategory(Category categoryTemp){
        for(Professor prof: allocatedToProf){
            if(prof.category == categoryTemp){
                return true;
            }
        }
        return false;
    }
    public void deAllocateThisCourse () {
        for (Professor prof : allocatedToProf) {
            prof.deAllocateCourse(this);
        }
        allocatedToProf.clear();
    }

    public Course(String courseName,String category) {
        this.category = category;
        this.courseName = courseName;
        this.courseWeight = 1;
        this.courseAvailable = 1;
    }
}