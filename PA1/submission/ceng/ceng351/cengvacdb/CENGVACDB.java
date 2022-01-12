package ceng.ceng351.cengvacdb;

import java.sql.*;
import java.util.Vector;
import java.util.ArrayList;


public class CENGVACDB implements ICENGVACDB {

    private static String user = "e2306090"; // TODO: Your userName
    private static String password = "BWpocA5u-g3v"; //  TODO: Your password
    private static String host = "144.122.71.121"; // host name
    private static String database = "db2306090"; // TODO: Your database name
    private static int port = 8080; // port

    private static Connection connection = null;

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(url, user, password);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int createTables() {
        int numberOfTablesCreated = 0;
        Vector<String> queries = new Vector<String>();
        String queryCreateUserTable = "Create Table User (" +
                "userID int not null," +
                "userName varchar(30)," +
                "age int," +
                "address varchar(150)," +
                "password varchar(30)," +
                "status varchar(15)," +
                "primary key (userID))";

        queries.add(queryCreateUserTable);

        String queryCreateVaccineTable = "Create Table Vaccine (" +
                "code int not null," +
                "vaccinename varchar(30)," +
                "type varchar(30)," +
                "primary key (code))";

        queries.add(queryCreateVaccineTable);


        String queryCreateVaccinationTable = "Create Table Vaccination (" +
                "code int not null," +
                "userID int not null," +
                "dose int not null," +
                "vacdate date," +
                "primary key (code,userID,dose),"+
                "foreign key (code) references Vaccine(code),"+
                "foreign key (userID) references User(userID)"+
                "on delete cascade)";

        queries.add(queryCreateVaccinationTable);


        String queryCreateAllergicSideEffectTable = "Create Table AllergicSideEffect (" +
                "effectcode int not null," +
                "effectname varchar(50)," +
                "primary key (effectcode))";

        queries.add(queryCreateAllergicSideEffectTable);

        String queryCreateSeenTable = "Create Table Seen (" +
                "effectcode int not null," +
                "code int not null," +
                "userID int not null," +
                "date date," +
                "degree varchar(30)," +
                "primary key (effectcode, code,userID),"+
                "foreign key (effectcode) references AllergicSideEffect(effectcode),"+
                "foreign key (code) references Vaccine(code),"+
                "foreign key (userID) references User(userID)"+
                "on delete cascade)";

        queries.add(queryCreateSeenTable);

        for (int i = 0; i < queries.size(); i++)
        {
            try {

                Statement statement = connection.createStatement();
                statement.executeUpdate(queries.get(i));
                numberOfTablesCreated++;
                statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }






        return numberOfTablesCreated;
    }

    @Override
    public int dropTables() {
        int numberOfTablesDropped = 0;
        Vector<String> queries = new Vector<String>();
        String queryDropSeenTable = "Drop Table if exists Seen";
        queries.add(queryDropSeenTable);
        String queryDropAllergicSideEffectTable = "Drop Table if exists AllergicSideEffect";
        queries.add(queryDropAllergicSideEffectTable);
        String queryDropVaccinationTable = "Drop Table if exists Vaccination";
        queries.add(queryDropVaccinationTable);
        String queryDropVaccineTable = "Drop Table if exists Vaccine";
        queries.add(queryDropVaccineTable);
        String queryDropUserTable = "Drop Table if exists User";
        queries.add(queryDropUserTable);





        for (int i = 0; i < queries.size(); i++)
        {
            try {

                Statement statement = connection.createStatement();
                statement.executeUpdate(queries.get(i));
                numberOfTablesDropped++;
                statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }



        return numberOfTablesDropped;
    }

    @Override
    public int insertUser(User[] users) {

        int numberOfRowsInserted = 0;

        for(int i=0; i< users.length; i++){

            String query = "insert into User(userID, userName, age, address, password, status) values (?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, users[i].getUserID());
                ps.setString(2, users[i].getUserName());
                ps.setInt(3, users[i].getAge());
                ps.setString(4, users[i].getAddress());
                ps.setString(5, users[i].getPassword());
                ps.setString(6, users[i].getStatus());
                ps.execute();
                numberOfRowsInserted++;


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return numberOfRowsInserted;
    }

    @Override
    public int insertAllergicSideEffect(AllergicSideEffect[] sideEffects) {
        int numberOfRowsInserted = 0;

        for(int i=0; i< sideEffects.length; i++){

            String query = "insert into AllergicSideEffect(effectcode, effectname) values (?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, sideEffects[i].getEffectCode());
                ps.setString(2, sideEffects[i].getEffectName());
                ps.execute();
                numberOfRowsInserted++;


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return numberOfRowsInserted;
    }

    @Override
    public int insertVaccine(Vaccine[] vaccines) {
        int numberOfRowsInserted = 0;

        for(int i=0; i< vaccines.length; i++){

            String query = "insert into Vaccine(code, vaccinename, type) values (?, ?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, vaccines[i].getCode());
                ps.setString(2, vaccines[i].getVaccineName());
                ps.setString(3, vaccines[i].getType());
                ps.execute();
                numberOfRowsInserted++;


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return numberOfRowsInserted;
    }

    @Override
    public int insertVaccination(Vaccination[] vaccinations) {
        int numberOfRowsInserted = 0;

        for(int i=0; i< vaccinations.length; i++){

            String query = "insert into Vaccination(code, userID, dose, vacdate) values (?, ?, ?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, vaccinations[i].getCode());
                ps.setInt(2, vaccinations[i].getUserID());
                ps.setInt(3, vaccinations[i].getDose());
                ps.setString(4, vaccinations[i].getVacdate());
                ps.execute();
                numberOfRowsInserted++;


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return numberOfRowsInserted;
    }

    @Override
    public int insertSeen(Seen[] seens) {
        int numberOfRowsInserted = 0;

        for(int i=0; i< seens.length; i++){

            String query = "insert into Seen(effectcode, code, userID, date, degree) values (?, ?, ?, ?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, seens[i].getEffectcode());
                ps.setInt(2, seens[i].getCode());
                ps.setString(3, seens[i].getUserID());
                ps.setString(4, seens[i].getDate());
                ps.setString(5, seens[i].getDegree());
                ps.execute();
                numberOfRowsInserted++;


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return numberOfRowsInserted;
    }

    @Override
    public Vaccine[] getVaccinesNotAppliedAnyUser() {
        ArrayList<Vaccine> resultList = new ArrayList<Vaccine>();
        Vaccine vac;
        Vaccine vaccines[];
        ResultSet queryResult;

        String query = "Select V.code, V.vaccinename, V.type "+
                            "From Vaccine V "+
                            "Where V.code not in ("+
                                    "Select Distinct N.code "+
                                        "From Vaccination N) "+
                            "Order By V.code asc";

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                int code= queryResult.getInt("code");
                String vaccinename = queryResult.getString("vaccinename");
                String type = queryResult.getString("type");

                vac = new Vaccine(code, vaccinename, type);
                resultList.add(vac) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vaccines = new Vaccine[resultList.size()];
        vaccines = resultList.toArray(vaccines);


        return vaccines;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersforTwoDosesByDate(String vacdate) {
        ArrayList<QueryResult.UserIDuserNameAddressResult> resultList = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
        QueryResult.UserIDuserNameAddressResult res;
        QueryResult.UserIDuserNameAddressResult result[];
        ResultSet queryResult;

        String q = "Select U.userID, U.userName, U.address " +
                                "From User U, (Select N.userID, Count(*) as doseNumber " +
                                                     "From Vaccination N " +
                                                     "Where N.vacdate >= '%s' " +
                                                     "Group By N.userID) as temp " +
                                "Where temp.doseNumber=2 and U.userID=temp.userID " +
                                "Order By U.userID asc";

        String query=String.format(q, vacdate);

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                String userID= queryResult.getString("userID");
                String userName = queryResult.getString("userName");
                String address = queryResult.getString("address");

                res = new QueryResult.UserIDuserNameAddressResult(userID, userName, address);
                resultList.add(res) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = new QueryResult.UserIDuserNameAddressResult[resultList.size()];
        result = resultList.toArray(result);


        return result;
    }

    @Override
    public Vaccine[] getTwoRecentVaccinesDoNotContainVac() {

        ArrayList<Vaccine> resultList = new ArrayList<Vaccine>();
        Vaccine vac;
        Vaccine vaccines[];
        ResultSet queryResult;


        String query = "Select Distinct code, vaccinename, type " +
                                "From (Select * " +
                                            "From Vaccine " +
                                                "Natural Join " +
                                            "(Select N.code, max(N.vacdate) as latestDate " +
                                                    "From Vaccination N Group By N.code) as temp " +
                                            "Where vaccinename not like '%vac%'  " +
                                            "Order By latestDate desc limit 2) as lastTable " +
                                "Order By code asc;";

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                int code= queryResult.getInt("code");
                String vaccinename = queryResult.getString("vaccinename");
                String type = queryResult.getString("type");

                vac = new Vaccine(code, vaccinename, type);
                resultList.add(vac) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vaccines = new Vaccine[resultList.size()];
        vaccines = resultList.toArray(vaccines);


        return vaccines;

    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersAtHasLeastTwoDoseAtMostOneSideEffect() {

        ArrayList<QueryResult.UserIDuserNameAddressResult> resultList = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
        QueryResult.UserIDuserNameAddressResult res;
        QueryResult.UserIDuserNameAddressResult result[];
        ResultSet queryResult;

        String query = "Select Distinct U.userID, U.userName, U.address " +
                        "From User U, Vaccination V " +
                        "Where U.userID = V.UserID and U.userID in " +
                                                        "((Select U2.UserID " +
                                                            "From User U2, Seen S " +
                                                            "Where U2.userID= S.userID  " +
                                                            "Group By U2.userId " +
                                                            "Having Count(*)<=1) " +
                                                                "union " +
                                                            "(Select U.userID " +
                                                                "from User U " +
                                                                "where U.userID not in " +
                                                                    "(Select Distinct S.userID " +
                                                                            "from Seen S))) " +
                                                                "Group By U.userID Having Count(*)>=2 " +
                        "Order By U.userID asc";


        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                String userID= queryResult.getString("userID");
                String userName = queryResult.getString("userName");
                String address = queryResult.getString("address");

                res = new QueryResult.UserIDuserNameAddressResult(userID, userName, address);
                resultList.add(res) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = new QueryResult.UserIDuserNameAddressResult[resultList.size()];
        result = resultList.toArray(result);



        return result;



    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect(String effectname) {

        ArrayList<QueryResult.UserIDuserNameAddressResult> resultList = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
        QueryResult.UserIDuserNameAddressResult res;
        QueryResult.UserIDuserNameAddressResult result[];
        ResultSet queryResult;

        String q = "Select U.userID, U.userName, U.address " +
                        "From User U " +
                        "Where not exists " +
                                "(Select S.code " +
                                    "From Seen S, AllergicSideEffect A " +
                                    "Where A.effectname = '%s'" +
                                    " and S.effectcode = A.effectcode and " +
                                    "not exists " +
                                         "(Select N.code" +
                                                " From Vaccination N " +
                                                "Where N.code=S.code and N.userID = U.userID)) " +
                        "Order By U.userID asc" ;

        String query=String.format(q, effectname);
        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                String userID= queryResult.getString("userID");
                String userName = queryResult.getString("userName");
                String address = queryResult.getString("address");

                res = new QueryResult.UserIDuserNameAddressResult(userID, userName, address);
                resultList.add(res) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = new QueryResult.UserIDuserNameAddressResult[resultList.size()];
        result = resultList.toArray(result);



        return result;

    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval(String startdate, String enddate) {

        ArrayList<QueryResult.UserIDuserNameAddressResult> resultList = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
        QueryResult.UserIDuserNameAddressResult res;
        QueryResult.UserIDuserNameAddressResult result[];
        ResultSet queryResult;


        String q = "Select U.userID, U.username, U.address " +
                                "From (Select N.userID, Count(Distinct V.type) as distinctTypeNumber " +
                                                "From Vaccination N, Vaccine V " +
                                                "Where N.code=V.code and N.vacdate >= '%1$s' and N.vacdate <= '%2$s' " +
                                                "Group By N.userID) as temp, User U " +
                                "Where temp.distinctTypeNumber>=2 and U.userID = temp.userID " +
                                "Order By U.userID asc";

        String query=String.format(q, startdate, enddate);

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                String userID= queryResult.getString("userID");
                String userName = queryResult.getString("userName");
                String address = queryResult.getString("address");

                res = new QueryResult.UserIDuserNameAddressResult(userID, userName, address);
                resultList.add(res) ;

            }
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        result = new QueryResult.UserIDuserNameAddressResult[resultList.size()];
        result = resultList.toArray(result);



        return result;


    }

    @Override
    public AllergicSideEffect[] getSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays() {

        ArrayList<AllergicSideEffect> resultList = new ArrayList<AllergicSideEffect>();
        AllergicSideEffect res;
        AllergicSideEffect result[];
        ResultSet queryResult;

        String query = "Select Distinct A.effectcode, A.effectname " +
                        "From AllergicSideEffect A, Seen S,  " +
                        "(Select N1.userID, Datediff(N1.vacdate, N2.vacdate) as datediff " +
                                "from Vaccination N1, Vaccination N2 " +
                                "where N1.userID = N2.userID  and N1.dose <> N2.dose) as temp " +
                        "where S.effectcode = A.effectcode and S.userID = temp.userID and temp.datediff <20 " +
                        "and temp.datediff > -20 " +
                        "Order By A.effectcode asc";

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            while( queryResult.next()) {

                int effectcode= queryResult.getInt("effectcode");
                String effectname = queryResult.getString("effectname");

                res = new AllergicSideEffect(effectcode, effectname);
                resultList.add(res) ;

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = new AllergicSideEffect[resultList.size()];
        result = resultList.toArray(result);



        return result;







    }

    @Override
    public double averageNumberofDosesofVaccinatedUserOverSixtyFiveYearsOld() {

        double result = 0;
        ResultSet queryResult;

        String query = "Select Avg(temp.doseNum) as avgDose " +
                            "from (Select N.userID, Max(N.dose) as doseNum " +
                                            "From Vaccination N " +
                                            "Where N.userID in (Select U.userID " +
                                                                    "from User U " +
                                                                    "where U.age > 65) " +
                                                                    "Group By N.userID) as temp";

        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);
            while (queryResult.next()){
                result = queryResult.getDouble("avgDose");

            }
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }


    @Override
    public int updateStatusToEligible(String givendate) {
        int numberOfRowsAffected = 0;


        String query = "Update User U " +
                                "Set U.status = 'Eligible' " +
                                "Where U.status = 'Not_Eligible' and " +
                                "U.userID in " +
                                            "(Select dateDiffTable.userID " +
                                                    "From (Select temp.userID, Datediff('2021-12-19',  temp.lastVacDate) as datediff " +
                                                                "From (Select V.userID, max(vacdate) as lastVacDate " +
                                                                                "From Vaccination V Group By V.userID ) as temp) as dateDiffTable " +
                                                    "Where dateDiffTable.datediff >=120 or dateDiffTable.datediff <= -120)";

        try {
            Statement statement = connection.createStatement();
            numberOfRowsAffected = statement.executeUpdate(query);
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return numberOfRowsAffected;
    }

    @Override
    public Vaccine deleteVaccine(String vaccineName) {
        Vaccine vac = null;
        ResultSet queryResult;
        String q = "Select * " +
                            "From Vaccine " +
                            "Where vaccinename = '%s'";

        String query=String.format(q, vaccineName);


        try {
            Statement statement = connection.createStatement();
            queryResult = statement.executeQuery(query);

            queryResult.next();

            int code= queryResult.getInt("code");
            String vaccinename = queryResult.getString("vaccinename");
            String type = queryResult.getString("type");

            vac = new Vaccine(code, vaccinename, type);

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        q = "Delete from Vaccine " +
                    "Where vaccinename = '%s'";

        query=String.format(q, vaccineName);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return vac;
    }
}
