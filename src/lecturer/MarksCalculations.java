/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturer;

import common.code.MyDbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author kavindu
 */
public class MarksCalculations implements MarksOperations{
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    DecimalFormat df = new DecimalFormat("#.##");

    double[] quizScores;
    public String courseID = "";
    public String status = "";
    public String Grade = "";
    public double midtermScore = 0.0;
    public double assessmentScore = 0.0;
    public double finalMarks = 0.0;
    private double caMarks = 0.0;
    public double quizAvg = 0.0;
    public double calculatedFinalPrac = 0.0;
    public double calculatedFinalTheory = 0.0;
    public double gpv = 0.0;
    public double max = 0.0;
    public double secondmax = 0.0;
    public double thirdmax = 0.0;


    /**
     * Calculates the average quiz score based on the course ID and quiz scores.
     *
     * @param courseID The ID of the course.
     * @param quizScores The array of quiz scores.
     * @return The calculated average quiz score.
     * @throws IllegalArgumentException if an invalid course ID is provided.
     */
    public double calculateQuizAvg(String courseID, double quiz1, double quiz2, double quiz3, double quiz4) {
    
    if (courseID.equals("ICT01") || courseID.equals("ICT03") || courseID.equals("ICT04") || courseID.equals("ICT05") || courseID.equals("TMS01")) {
        
        // Create an array of the quiz scores
        double[] quizScores = {quiz1, quiz2, quiz3};
        
        // Sort the array in descending order
        Arrays.sort(quizScores);
        
        for (int i = 0; i < quizScores.length / 2; i++) {
        double temp = quizScores[i];
        quizScores[i] = quizScores[quizScores.length - 1 - i];
        quizScores[quizScores.length - 1 - i] = temp;
        }
        
         // Get the maximum, second maximum, and third maximum values
        double max = quizScores[0];
        double secondMax = quizScores[1];
       
        // Calculate the quiz average
        double quizAvg = ((max + secondMax)/2.0) * 0.1;
        
        return Double.parseDouble(df.format(quizAvg));
        
        
    } else if ( courseID.equals("ICT02")) {
        // Create an array of the quiz scores
        double[] quizScores = {quiz1, quiz2, quiz3, quiz4};

        // Sort the array in descending order
        Arrays.sort(quizScores);
        
        for (int i = 0; i < quizScores.length / 2; i++) {
        double temp = quizScores[i];
        quizScores[i] = quizScores[quizScores.length - 1 - i];
        quizScores[quizScores.length - 1 - i] = temp;
        }

        // Get the maximum, second maximum, and third maximum values
        double max = quizScores[0];
        double secondMax = quizScores[1];
        double thirdMax = quizScores[2];

        // Calculate the quiz average
        double quizAvg = ((max + secondMax + thirdMax)/3.0) * 0.1;
        
        return Double.parseDouble(df.format(quizAvg));
    } else {
        throw new IllegalArgumentException("Invalid course ID");
    }
}



    /**
     * Calculates the midterm score based on the course ID and the midterm score.
     *
     * @param courseID The ID of the course.
     * @param midterm  The midterm score.
     * @return The calculated midterm score.
     */
    public double calculateMidtermScore(String courseID, double midterm) {
        if (courseID.equals("ICT01") || courseID.equals("ICT02")) {
            double score = midterm * 0.2;
            return Double.parseDouble(df.format(score));
        } else {
            return 0.0;
        }
    }



    /**
     * Calculates the assessment score based on the course ID and assignment scores.
     *
     * @param courseID     The ID of the course.
     * @param assignment01 The score of assignment 01.
     * @param assignment02 The score of assignment 02.
     * @return The calculated assessment score.
     */
    public double calculateAssessmentScore(String courseID, double assignment01, double assignment02) {
        if (courseID.equals("ICT03") || courseID.equals("ICT04") || courseID.equals("ICT05") || courseID.equals("TMS01")) {
            double score = ((assignment01 + assignment02) / 2) * 0.2;
            return Double.parseDouble(df.format(score));
        } else if (courseID.equals("ICT02")) {
            double score = ((assignment01 + assignment02) / 2) * 0.1;
            return Double.parseDouble(df.format(score));
        } else {
            return 0.0;
        }
    }



    /**
     * Calculates the cumulative assessment marks based on the quiz average, midterm score, and assessment score.
     *
     * @param quizAvg         The average quiz score.
     * @param midtermScore    The midterm score.
     * @param assessmentScore The assessment score.
     * @return The calculated cumulative assessment marks.
     */
    public double calculateCAMarks(double quizAvg, double midtermScore, double assessmentScore) {
        double caMarks = quizAvg + midtermScore + assessmentScore;
        return Double.parseDouble(df.format(caMarks));
    }


    /**
     * Checks the eligibility of a student for a given course based on their cumulative assessment marks.
     *
     * @param courseID The ID of the course.
     * @param caMarks  The cumulative assessment marks.
     * @return The eligibility status ("Eligible" or "Not Eligible").
     */
    public String checkEligibility(String courseID, String studentID, double caMarks) {
        String status;

        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");
            try {
                connection = dbConnector.getMyConnection();

                // Prepare the SQL query to fetch eligibility records
                String query = "SELECT session, eligibility FROM attendanceEligibility WHERE courseID = ? AND userID = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, courseID);
                pstmt.setString(2, studentID);

                // Execute the query and retrieve the result set
                ResultSet rs = pstmt.executeQuery();

                boolean hasTheory = false;
                boolean hasPractical = false;
                boolean eligible = true;

                while (rs.next()) {
                    String session = rs.getString("session");
                    String eligibility = rs.getString("eligibility");

                    if (session.equals("theory")) {
                        hasTheory = true;
                        if (eligibility.equals("Not Eligible")) {
                            status = "Not Eligible";
                            return status;
                        }
                    } else if (session.equals("practical")) {
                        hasPractical = true;
                        if (eligibility.equals("Not Eligible")) {
                            status = "Not Eligible";
                            return status;
                        }
                    }

                    if (eligibility.equals("Not Eligible")) {
                        eligible = false;
                    }
                }

                if (hasTheory && hasPractical) {
                    if (eligible && caMarks >= 20) {
                        status = "Eligible";
                    } else {
                        status = "Not Eligible";
                    }
                } else {
                    if (caMarks >= 15) {
                        status = "Eligible";
                    } else {
                        status = "Not Eligible";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                status = "Not Eligible";
            } finally {
                // Close the database connection in the finally block
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error in closing database connection: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("dbConnector not assigned: " + e.getMessage());
            status = "Not Eligible";
        }

        return status;
    }






    /**
     * Calculates the final practical marks based on eligibility, course ID, and final practical score.
     *
     * @param eligibility   The eligibility status ("Eligible" or "Not Eligible").
     * @param courseID      The ID of the course.
     * @param finalPrac     The final practical score.
     * @return The calculated final practical marks.
     */
    public double calculateFinalPracMarks(String eligibility, String courseID, double finalPrac) {
        double calculatedFinalPrac = 0.0;
        if (eligibility.equals("Eligible")) {
            if (courseID.equals("ICT01") || courseID.equals("ICT03")) {
                calculatedFinalPrac = finalPrac * 0.3;
            } else if (courseID.equals("ICT04") || courseID.equals("ICT05") || courseID.equals("TMS01")) {
                calculatedFinalPrac = finalPrac * 0.4;
            }
        }
        return Double.parseDouble(df.format(calculatedFinalPrac));
    }


    /**
     * Calculates the final theory marks based on eligibility, course ID, and final theory score.
     *
     * @param eligibility  The eligibility status ("Eligible" or "Not Eligible").
     * @param courseID     The ID of the course.
     * @param finalTheory  The final theory score.
     * @return The calculated final theory marks.
     */
    public double calculateFinalTheoryMarks(String eligibility, String courseID, double finalTheory) {
        if (eligibility.equals("Eligible")) {
            if (courseID.equals("ICT01") || courseID.equals("ICT03")) {
                double marks = finalTheory * 0.4;
                return Double.parseDouble(df.format(marks));
            } else if (courseID.equals("ICT04") || courseID.equals("ICT05") || courseID.equals("TMS01")) {
                double marks = finalTheory * 0.3;
                return Double.parseDouble(df.format(marks));
            } else if (courseID.equals("ICT02")) {
                double marks = finalTheory * 0.6;
                return Double.parseDouble(df.format(marks));
            }
        }
        return 0.0;
    }



    /**
     * Calculates the final marks based on eligibility, CA marks, calculated final theory marks, and calculated final practical marks.
     *
     * @param eligibility             The eligibility status ("Eligible" or "Not Eligible").
     * @param caMarks                 The CA marks.
     * @param calculatedFinalTheory   The calculated final theory marks.
     * @param calculatedFinalPrac     The calculated final practical marks.
     * @return The calculated final marks.
     */
    public double calculateFinalMarks(String eligibility, double caMarks, double calculatedFinalTheory, double calculatedFinalPrac) {
        if (eligibility.equals("Eligible")) {
            double finalMarks = caMarks + calculatedFinalTheory + calculatedFinalPrac;
            return Double.parseDouble(df.format(finalMarks));
        } else {
            return 0.0;
        }
    }



    /**
     * Finds the grade based on eligibility and marks.
     *
     * @param eligibility The eligibility status ("Eligible" or "Not Eligible").
     * @param marks       The marks.
     * @return The corresponding grade.
     */
    public String findGrade(String eligibility, double marks) {
        if (!eligibility.equals("Eligible")) {
            return "NE";
        }

        if (marks >= 80 && marks <= 100) {
            return "A+";
        } else if (marks >= 75) {
            return "A";
        } else if (marks >= 70) {
            return "A-";
        } else if (marks >= 65) {
            return "B+";
        } else if (marks >= 60) {
            return "B";
        } else if (marks >= 55) {
            return "B-";
        } else if (marks >= 50) {
            return "C+";
        } else if (marks >= 45) {
            return "C";
        } else if (marks >= 40) {
            return "C-";
        } else if (marks >= 30) {
            return "D";
        } else if (marks >= 0) {
            return "E";
        } else {
            return "Not Valid";
        }
    }


    /**
     * Calculates the Grade Point Value (GPV) based on eligibility and grade.
     *
     * @param eligibility The eligibility status ("Eligible" or "Not Eligible").
     * @param grade       The grade.
     * @return The calculated GPV.
     * @throws IllegalArgumentException if there's an error in calculating GPV.
     */
    public double calculateGPV(String eligibility, String grade) {
        if (!eligibility.equals("Eligible")) {
            return 0.0;
        }

        double gpv;
        switch (grade) {
            case "A+":
            case "A":   gpv = 4.0; break;
            case "A-":  gpv = 3.7; break;
            case "B+":  gpv = 3.3; break;
            case "B":   gpv = 3.0; break;
            case "B-":  gpv = 2.7; break;
            case "C+":  gpv = 2.3; break;
            case "C":   gpv = 2.0; break;
            case "C-":  gpv = 1.7; break;
            case "D+":  gpv = 1.3; break;
            case "D":   gpv = 1.0; break;
            case "E":
            case "E*":
            case "F":   gpv = 0.0; break;
            default:    throw new IllegalArgumentException("Error in calculating GPV");
        }

        System.out.println(gpv);
        return gpv;
    }
}

