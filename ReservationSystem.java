
    import java.sql.*;
    import java.util.*;
    public class ReservationSystem{
        private String user;
        private String password;
        private String Source;
        private String Destination;
        private String date;
        private String name;
        private String gender;
        private int Age;
        private int pnr;
        private int trainnumber;
        private String trainname;
        Scanner sc=new Scanner(System.in);
        Random random=new Random();
         ReservationSystem(){
                 
        }
        public String getUser(){
            System.out.println("Enter UserName: ");
            user=sc.nextLine();
            return user;
        }
        public String getPassword(){
            System.out.println("Enter Password: ");
            password=sc.nextLine();
            return password;
        }
        public String getSource(){
            System.out.println("Enter SourceStation: ");
            Source=sc.next();
            return Source;
        }
        public String getDestination(){
            System.out.println("Enter Destination: ");
            Destination=sc.next();
            return Destination;
        }
        public String getDate(){
            System.out.println("Enter Date of Travel(DD-MM-YYYY): ");
            date=sc.next();
            return date;
        }
        public String getName(){
            System.out.println("Enter Name: ");
            name=sc.next();
            return name;
        }
        public String getGender(){
            System.out.println("Enter Gender(M-Male,F-Female,O-Others): ");
            gender=sc.next();
            return gender;
        }
        public int getAge(){
            System.out.println("Enter Age: ");
            Age=sc.nextInt();
            return Age;
        }
        public int getPnr(){
            pnr=random.nextInt(99999)+11111;
            return pnr;
        }
        public int getNumber(){
            System.out.println("Enter Train Number ");
            trainnumber=sc.nextInt();
            return trainnumber;
        }
        public String getTName(){
            System.out.println("Enter Train Name");
            trainname=sc.next();
            return trainname;
        }
        void display(String delname,String username,String pass){
            String url="jdbc:mysql://localhost:3306/task1";
            String sql="select * from reservation where pname= \""+delname+"\";";
                try(Connection conn=DriverManager.getConnection(url,username,pass);Statement stmt=conn.createStatement()){
                    try(ResultSet rs1=stmt.executeQuery(sql)){  
                  
                        while (rs1.next()) {
                                String zname=rs1.getString("pname");
                                String zgender=rs1.getString("pgender");
                                String zsource=rs1.getString("psource");
                                String zdestination=rs1.getString("pdestination");
                                int zpnr=rs1.getInt("pnr");
                                String zdate=rs1.getString("pdate");
                                int zage=rs1.getInt("age");
                                String ztname=rs1.getString("trainname");
                                int znum=rs1.getInt("trainnumber");
                                System.out.println("Name:"+zname);
                                System.out.println("Gender:"+zgender);
                                System.out.println("Source:"+zsource);
                                System.out.println("Destiation:"+zdestination);
                                System.out.println("PNR No.:"+zpnr);
                                System.out.println("Date:"+zdate);
                                System.out.println("AGE:"+zage);
                                System.out.println("Train Name: "+ztname);
                                System.out.println("Train No.: "+znum);
                        }
                    
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args){
            Scanner sc=new Scanner(System.in);
            ReservationSystem r1=new ReservationSystem();
            System.out.println("Enter UserID and Password to continue");
            String username=r1.getUser();
            String password=r1.getPassword();
            String url="jdbc:mysql://localhost:3306/task1";
            while(true){
            System.out.println("Choose the operation you want to perform");
            System.out.println("1.New Passenger info");
            System.out.println("2.Show Booking Details");
            System.out.println("3.Cancel Booking");
            System.out.println("4.Exit");
    
            System.out.println("Enter Choice number");
            int option=sc.nextInt();
            if(option==1){
            String pname=r1.getName();
            String pgender=r1.getGender();
            String source=r1.getSource();
            String destination=r1.getDestination();
            String pdate=r1.getDate();
            int page=r1.getAge();
            int pnr=r1.getPnr();
            int number=r1.getNumber();
            String Tname=r1.getTName();
            
            String sql="insert into reservation (pname,pgender,psource,pdestination,pnr,pdate,age,trainname,trainnumber) values (?,?,?,?,?,?,?,?,?);";
           
            
            try(Connection connection=DriverManager.getConnection(url, username, password);PreparedStatement pstmt = connection.prepareStatement(sql)){
                pstmt.setString(1, pname);
                pstmt.setString(2, pgender);
                pstmt.setString(3, source);
                pstmt.setString(4, destination);
                pstmt.setInt(5, pnr);
                pstmt.setString(6, pdate);
                pstmt.setInt(7, page);
                pstmt.setString(8,Tname);
                pstmt.setInt(9,number);
    ;           
                pstmt.executeUpdate();
                System.out.println("New Passenger info addition successfull");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
            else if(option==2){
    
            System.out.println("Enter Name of passenger details to be displayed");
                String Name2=sc.next();
                r1.display(Name2,username,password);
            }
            else if(option==3){
                System.out.println("Enter Name of the Passenger whose record you wish to be deleted");
                String delname=sc.next();
                String sql3="delete from reservation where pname= ? ;";
                try(Connection conn=DriverManager.getConnection(url,username,password);PreparedStatement stmt3=conn.prepareStatement(sql3)){
                    stmt3.setString(1,delname);
                    r1.display(delname,username,password);
                        System.out.println("Are you sure? press 1 to delete else 0 to exit");
                        int confirm=sc.nextInt();
                        if(confirm==1){
                            stmt3.executeUpdate();
                            System.out.println("Record Successfully deleted");
                        }
                        else if(confirm==0){
                            break;
                        }
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                        System.out.println("No Such Passenger exists");
                    }
                }
                    
            else if(option==4){
                break;
            }
    
    
        }
        sc.close();
    
        }
    }
    
     

