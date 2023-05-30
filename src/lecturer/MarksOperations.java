/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturer;

/**
 *
 * @author Kavindu_Dilhara
 */
public interface MarksOperations {
    
    public double calculateQuizAvg(String courseID, double quiz1, double quiz2, double quiz3, double quiz4);
    
    public double calculateMidtermScore(String courseID, double midterm);
    
    public double calculateAssessmentScore(String courseID, double assignment01, double assignment02);
    
    public double calculateCAMarks(double quizAvg, double midtermScore, double assessmentScore);
    
    public String checkEligibility(String courseID,String studentID, double caMarks);
    
    public double calculateFinalPracMarks(String eligibility, String courseID, double finalPrac);
    
    public double calculateFinalTheoryMarks(String eligibility, String courseID, double finalTheory);
    
    public double calculateFinalMarks(String eligibility, double caMarks, double calculatedFinalTheory, double calculatedFinalPrac);
    
    public String findGrade(String eligibility, double marks);
    
    public double calculateGPV(String eligibility, String grade);
}
