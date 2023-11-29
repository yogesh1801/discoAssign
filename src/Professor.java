import java.util.ArrayList;

class Professor {

    public String name;
    public Category category;
    public double possibleWorkLoad;
    public double workLoadAllocated;
    public ArrayList<String> coursesAllocated;
    public ArrayList<String> preferenceList;

    public boolean isCourseAllocable (Course course){
        if(this.preferenceList.contains(course.courseName)){
        if ((this.possibleWorkLoad - this.workLoadAllocated) >= 0.5 ) {
            return true;
        } else  {
            return false;
        }
        }
        return false;
    }

    public void allocateCourse (Course course) {
        coursesAllocated.add(course.courseName);
        course.allocatedToProf.add(this);
        double remaining = (this.possibleWorkLoad - this.workLoadAllocated);
        if(remaining >= course.courseAvailable) {
            this.workLoadAllocated += course.courseAvailable;
            course.courseAvailable = 0;
        }
        else{
            course.courseAvailable -= remaining;
            this.workLoadAllocated = this.possibleWorkLoad;

        }
    }

    public void deAllocateCourse(Course course) {
        coursesAllocated.remove(course.courseName);
        this.workLoadAllocated -= 0.5;
        course.courseAvailable = 1;
    }

    public int getPreferenceIndex(String courseName)
    {
        if(this.preferenceList.contains(courseName)){
            {
                return preferenceList.indexOf(courseName);
            }
        }
        else{
            return 10000;
        }
    }

    public String toString(){
        return "Professor name is: " + this.name + "\nCategory is: " + this.category + "\nThe work allocated is: " + this.workLoadAllocated + "\nThe courses allocated are: " + this.coursesAllocated + "\n";
    }

    public Professor(String name, Category category, double possibleWorkLoad , ArrayList<String> preferenceList) {
        this.name = name;
        this.category = category;
        this.possibleWorkLoad = possibleWorkLoad;
        this.workLoadAllocated = 0;
        this.coursesAllocated = new ArrayList<>();
        this.preferenceList = preferenceList;
    }
}