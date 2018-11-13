import java.io.* ;
import java.sql.*;
import java.sql.Date;
import java.util.*;

class CreateMovieTest {

    public static void main(String args[]){

        // Program title
        System.out.println("******************************************************************\n");
        System.out.println("                      Welcome to MovieDirect\n");
        System.out.println("******************************************************************");

        // Database connection
        // Create a new InputStreamReader and connecting to STDIN
        InputStreamReader istream = new InputStreamReader(System.in);
        // Create a new BufferedReader and connect it to the InputStreamReader
        BufferedReader bufRead = new BufferedReader(istream);

        String userName= "";
        String dbName= "";
        String password= "";

        Connection conn = null;
        // Loop through the database connection process until a successful connection is made
        // Once the connection has been made break from the loop
        while(true){
		    try{
		        System.out.print("\nDatabase: ");
		        dbName = bufRead.readLine();

		        System.out.print("Username: ");
		        userName = bufRead.readLine();

		        System.out.print("Password: ");
		        password = bufRead.readLine();

		        Class.forName("org.postgresql.Driver");
		        String url = "jdbc:postgresql://localhost/" + userName + "_" + dbName;
		        conn = DriverManager.getConnection(url,userName, password);
		        break;
		    }
	        catch (IOException err) {
		    	System.out.println(err);
		    	System.exit(0);
		    }
		    catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        System.exit(1);
		    }
		    // Unsuccessful connections are caught by the SQLException
		    catch (SQLException e) {
		        System.out.println("Unable to connect to the database, please try again");
		    }
	    }

        String movie_id = "";
        int movie_id_int = -1;
        String movie_title= "";
        String director_first_name= "";
        String director_last_name= "";
        String genre= "";
        String media_type = "";
        String release_date = "";
        String studio_name= "";
        String retail_price= "";
        double retail_price_double= -1;
        String current_stock= "";
        int current_stock_int= -1;

        // User inputs
        // Calls the method for movie id
        movie_id_int = movieId(bufRead, movie_id, movie_id_int);
        // Calls the method for movie title
        movie_title = movieTitle(bufRead, movie_title);
        // Calls the method for director first name
        director_first_name = directorFirstName(bufRead, director_first_name);
        // Calls the method for director first name
        director_last_name = directorLastName(bufRead, director_last_name);
        // Calls the method for movie genre
        genre = movieGenre(bufRead, genre);
        // Calls the method for media type
        media_type = mediaType(bufRead, media_type);
        // Calls the method for release date
        release_date = releaseDate(bufRead, release_date);
        // Calls the method for studio name
        studio_name = studioName(bufRead, studio_name);
        // Calls the method for retail price
        retail_price_double = retailPrice(bufRead, retail_price, retail_price_double);
        // Calls the method for copies in stock
        current_stock_int = currentStock(bufRead, current_stock, current_stock_int);


        Statement stmt = null;

        while(true){
            try {
                //Create a new statement object
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //Get all record in the employee table
                ResultSet uprs = stmt.executeQuery("SELECT * FROM Movie");

                //Create a new row in the ResultSet object
                uprs.moveToInsertRow();
                //Add new employee's information to the new row of data

                uprs.updateInt("movie_id", movie_id_int );
                uprs.updateString("movie_title", movie_title );
                uprs.updateString("director_first_name", director_first_name );
                uprs.updateString("director_last_name", director_last_name );
                uprs.updateString("genre", genre );
                uprs.updateString("media_type", media_type );
                uprs.updateDate("release_date", Date.valueOf(release_date));
                uprs.updateString("studio_name", studio_name );
                uprs.updateDouble("retail_price", retail_price_double );
                uprs.updateInt("current_stock", current_stock_int );

                //Insert the new row of data to the database
                uprs.insertRow();
                //Move the cursor back to the start of the ResultSet object
                uprs.beforeFirst();
                conn.close();
                System.out.println("\nSuccess! A new entry for " + movie_title + " has been entered into the database.");

                break;

            }
            catch (SQLException e ) {
                // Error Code 23505 relates to a unique violation in PostgreSQL
                String error_code = e.getSQLState();
                if(error_code.equals("23505")){
                    System.out.println("A movie with that id already exists. Please try again.");
                    movie_id_int = movieId(bufRead, movie_id, movie_id_int);
                }
                else{

                }
            }
        }
    }


    public static int movieId(BufferedReader bufRead, String movie_id, int movie_id_int){
        // Loop through the input for movie id until an integer has been entered, and then break from the loop
        while(true){
            try {
                System.out.print("\nPlease enter the id for the new movie: ");
                movie_id = bufRead.readLine();
                // Change input from String to Integer
                movie_id_int = Integer.parseInt(movie_id);
                break;
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(2);
            }
            // Non-integer inputs are caught by the NumberFormatException, including a Null input
            catch (NumberFormatException err) {
                System.out.println("A movie id needs to be entered and it must be an integer. Please try again.");
            }
        }
        return(movie_id_int);
    }

    public static String movieTitle(BufferedReader bufRead, String movie_title){
        // Loop through the input for movie title until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the title for the new movie: ");
                movie_title = bufRead.readLine();
                // Ensure that a value has be entered by checking the String length is > 0
                // Ensure that the input does not exceed 100 characters
                if(movie_title.length() > 0 && movie_title.length() < 101){
                    break;
                }
                else{
                    System.out.print("A movie title must be entered and it can be up to 100 characters long. Please try again.\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(3);
            }
        }
        return(movie_title);
    }

    public static String directorFirstName(BufferedReader bufRead, String director_first_name){
        // Loop through the input for director's first name until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the director's first name: ");
                director_first_name = bufRead.readLine();
                // Ensure that a value has be entered by checking the String length is > 0
                // Ensure that the input does not exceed 50 characters
                if(director_first_name.length() > 0 && director_first_name.length() < 51){
                    break;
                }
                else{
                    System.out.print("A director's first name must be entered and it can be up to 50 characters long. Please try again.\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(4);
            }
        }
        return(director_first_name);
    }

    public static String directorLastName(BufferedReader bufRead, String director_last_name){
        // Loop through the input for director's last name until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the director's last name: ");
                director_last_name = bufRead.readLine();
                // Ensure that a value has be entered by checking the String length is > 0
                // Ensure that the input does not exceed 50 characters
                if(director_last_name.length() > 0 && director_last_name.length() < 51){
                    break;
                }
                else{
                    System.out.print("A director's last name must be entered and it can be up to 50 characters long. Please try again. \n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(5);
            }
        }
        return(director_last_name);
    }

    public static String movieGenre(BufferedReader bufRead, String genre){
        // Loop through the input for genre until the correct input matching the pre-defined list is entered
        // Continue looping through until stateGenre becomes True
        // Set stateGenre as False
        Boolean stateGenre = false;
        while(stateGenre==false){
            try {
                System.out.print("Please enter the genre of the movie: ");
                genre = bufRead.readLine();
                // Change input so each word begins with a capital, always input to be more user friendly
                String[] splitInput = genre.split(" ");
                List<String> genreList = new ArrayList<String>();
                for(String item : splitInput){
                    genreList.add(item.substring(0, 1).toUpperCase() + item.substring(1));
                }
                genre = String.join(" ", genreList);
                // Create an array with the pre-defined genre types
                String[] genre_array = {"Action", "Adventure", "Comedy", "Romance", "Science Fiction", "Documentary", "Drama", "Horror"};
                // Loop through the array and check if each item matches the input, if so change stateGenre to True and break the loop
                for(String item : genre_array){
                    if(item.equals(genre)){
                        stateGenre=true;
                        break;
                    }
                }
                // Check if stateGenre is still false, if so print the following statements
                if(stateGenre==false){
                    System.out.print("A movie genre must be entered which matches one of the following:\n");
                    System.out.print("Action, Adventure, Comedy, Romance, Science Fiction, Documentary, Drama, Horror\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(6);
            }
        }
        return(genre);
    }

    public static String mediaType(BufferedReader bufRead, String media_type){
        // Loop through the input for media type until the correct input matching the pre-defined list is entered
        // Continue looping through until stateMedia becomes True
        // Set stateMedia as False
        Boolean stateMedia = false;
        while(stateMedia==false){
            try {
                System.out.print("Please enter the media type: ");
                media_type = bufRead.readLine();
                // Make input more user friendly by allowing different versions of DVD and Blu-Ray to be entered
                if(media_type.equals("dvd") || media_type.equals("Dvd")){
                    media_type = "DVD";
                }
                if(media_type.equals("blu-ray") || media_type.equals("Blu-ray")){
                    media_type = "Blu-Ray";
                }
                // Create an array with the pre-defined media types
                String[] media_array = {"DVD", "Blu-Ray"};
                // Loop through the array and check if each item matches the input, if so change stateMedia to True and break the loop
                for(String item : media_array){
                    if(item.equals(media_type)){
                        stateMedia=true;
                        break;
                    }
                }
                // Check if stateMedia is still false, if so print the following statements
                if(stateMedia==false){
                    System.out.print("A movie media type must be entered which matches one of the following:\n");
                    System.out.print("DVD, Blu-Ray\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(7);
            }
        }
        return(media_type);
    }



    public static String releaseDate(BufferedReader bufRead, String release_date){
        // Loop through the input for release date until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the movie's release date (yyyy-mm-dd): ");
                release_date = bufRead.readLine();

                // Split up the input and create a list containing 3 items; year, month and day
                String[] brokenInput = release_date.split("-");
                // Change the items at positions 1 (the month) & 2 (the day) to Integers
                // No need to change position 0 (year) to an Integer
                int release_month = Integer.parseInt(brokenInput[1]);
                int release_day = Integer.parseInt(brokenInput[2]);
                // Ensure that the input has been entered correctly by checking the lengths of each item in the list
                Boolean checkLengths = brokenInput[0].length() == 4 && brokenInput[1].length() == 2 && brokenInput[2].length() == 2;
                // Ensure that month entered does not exceed 12
                Boolean checkMonth = release_month < 13;
                // Ensure days entered match associated month
                int numDays = -1;
                if(release_month == 2){
                    numDays = 29;
                }
                else if(release_month == 4 || release_month == 6 || release_month == 9 || release_month == 11){
                    numDays = 30;
                }
                else{
                    numDays = 31;
                }
                Boolean checkDay = release_day <= numDays;
                // Ensure the input format is correct by checking there are dashes and they are in the correct positions
                String dashOne = Character.toString(release_date.charAt(4));
                String dashTwo = Character.toString(release_date.charAt(7));
                Boolean checkDash = dashOne.equals("-") && dashTwo.equals("-");

                // If all checks are True, break from the loop
                if(checkDash==true && checkLengths==true && checkMonth==true && checkDay==true){
                    break;
                }
                else{
                    System.out.print("Please enter the release date in the following format, yyyy-mm-dd\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(8);
            }
            catch (NumberFormatException err) {
                System.out.print("Please enter the release date in the following format, yyyy-mm-dd\n");
            }
            catch (StringIndexOutOfBoundsException err) {
                System.out.print("Please enter the release date in the following format, yyyy-mm-dd\n");
            }
            catch (ArrayIndexOutOfBoundsException err) {
                System.out.print("Please enter the release date in the following format, yyyy-mm-dd\n");
            }
        }
        return(release_date);
    }

    public static String studioName(BufferedReader bufRead, String studio_name){
            // Loop through the input for movie studio name until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the movie's studio: ");
                studio_name = bufRead.readLine();
                // Ensure that the input does not exceed 50 characters
                if(studio_name.length() < 51){
                    break;
                }
                else{
                    System.out.print("A studio name can be up to 50 characters long. Please try again\n");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(9);
            }
        }
        return(studio_name);
    }

    public static double retailPrice(BufferedReader bufRead, String retail_price, double retail_price_double){
        // Loop through the input for retail price until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the retail price of the Movie: ");
                retail_price = bufRead.readLine();
                retail_price_double = Double.parseDouble(retail_price);
                // Ensure that the input is greater then 0
                if(retail_price_double > 0){
                    break;
                }
                else{
                    System.out.print("The retail price needs to be a positive value, please try again");
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(10);
            }
            catch (NumberFormatException err) {
                System.out.println("The retail price needs to have a value greater than 0, please try again");
            }
        }
        return(retail_price_double);
    }


    public static int currentStock(BufferedReader bufRead, String current_stock, int current_stock_int){
        // Loop through the input for retail price until entered correctly, and then break from the loop
        while(true){
            try {
                System.out.print("Please enter the number of copies in stock: ");
                current_stock = bufRead.readLine();
                current_stock_int = Integer.parseInt(current_stock);
                if(current_stock_int >= 0){
                    break;
                }
            }
            catch (IOException err) {
                System.out.println(err);
                System.exit(11);
            }
            catch (NumberFormatException err) {
                System.out.println("The number of copies in stock should have a value of 0 or more, please try again");
            }
        }
        return(current_stock_int);
    }
}
