package org.ahilmi.pro1_sm_2.dto;

import java.time.LocalDate;

public class Teaches {

    private int courseId;       // teaches.courses_id
    private int professorId;    // teaches.professor_id
    private LocalDate startDate;
    private LocalDate endingDate;
    private int studentCount;

    public Teaches() {
    }

    public Teaches(int courseId, int professorId, LocalDate startDate, LocalDate endingDate, int studentCount) {
        this.courseId = courseId;
        this.professorId = professorId;
        this.startDate = startDate;
        this.endingDate = endingDate;
        this.studentCount = studentCount;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndingDate() { return endingDate; }
    public void setEndingDate(LocalDate endingDate) { this.endingDate = endingDate; }

    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }

    @Override
    public String toString() {
        return "Teaches{" +
                "courseId=" + courseId +
                ", professorId=" + professorId +
                ", startDate=" + startDate +
                ", endingDate=" + endingDate +
                ", studentCount=" + studentCount +
                '}';
    }
}