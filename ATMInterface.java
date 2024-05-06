import java.util.*;
import java.sql.*;
public class ATMInterface {
    public String Aholder;
   public int Aid;
   public int balance;
   public String Branch;
   public String Username;
   public int Pin;
   public String url="jdbc:mysql://localhost:3306/ATM";
   public String User="root";
   public String Pass="Rohit123";
   Scanner sc=new Scanner(System.in);
    ATMInterface(){

    }
    String getholder(){
        System.out.println("Enter Account Holder Name ");
        Aholder=sc.next();
        return Aholder;
    }
    int getID(){
        Random rd=new Random();
        Aid=rd.nextInt(999999)+111111;
        return Aid;
    }
    String getbranch(){
        System.out.println("Choose your Branch\n1.Banglore\n2.Hyderbad\n3.Chennai");
        int choice=sc.nextInt();
        switch(choice){
            case 1:
            Branch="Banglore";
            break;
            case 2:
            Branch="Hyderbad";
            break;
            case 3:
            Branch="Chennai";
            break;
            default:
            System.out.println("Invalid Option selected");
            System.exit(1);
        }
        return Branch;
    }
    String getUser(){
        System.out.print("Username: ");
        Username=sc.next();
        System.out.println();
        return Username;
    }
    int getPin(){
        System.out.print("Pin(only Integer): ");
        Pin=sc.nextInt();
        System.out.println();
        return Pin;
    }
    int getdetails(){
        System.out.println("Enter Username and Password");
        String user=getUser();
        int pin=getPin();
        int pin1=0,Aid=0;
        String sql="select * from checker where Username= ?;";
        try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,user);
            try(ResultSet rs=pstmt.executeQuery()){
                while (rs.next()) {
                     pin1=rs.getInt("Pin");
                     Aid=rs.getInt("Aid");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }
    catch(SQLException e){
        e.printStackTrace();
    }
    if(pin==pin1){
        System.out.println("Authentication Succesfull");
        return Aid;
    }
    else{
        System.out.println("Invalid Username or Password");
        System.exit(0);
        return 0;
    }
}

    void display(){
        Aid=getdetails();
        String sql="select * from Interface where Aid= "+String.valueOf(Aid)+";";
        try(Connection conn=DriverManager.getConnection(url,User,Pass);Statement stmt=conn.createStatement()){
            try(ResultSet rs=stmt.executeQuery(sql)){
                while(rs.next()){
                    String name=rs.getString("Aholder");
                    int balance=rs.getInt("Abalance");
                    String history=rs.getString("History");
                    System.out.println("Account Holder: "+name);
                    System.out.println("Account Number: "+Aid);
                    System.out.println("Balance: "+balance);
                    System.out.println("Last Transaction: "+history);
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
        
        String url="jdbc:mysql://localhost:3306/ATM";
        String User="root";
        String Pass="Rohit123";
        Scanner sc=new Scanner(System.in);
        ATMInterface r=new ATMInterface();
     while(true){
        System.out.println("Welcome to ABC ATM");
        System.out.println("Choose the operation you wanted to perform");
        System.out.println("1.ADD USER\n2.WITHDRAW\n3.DEPOSIT\n4.TRANSFER\n5.VIEW ACCOUNT DETAILS(BALANCE/HISTORY)\n6.EXIT");
        int option=sc.nextInt();
        if(option==1){
            String sql="insert into Interface (Aid,Aholder,Abranch,Abalance) values (?,?,?,?);";
            String sql2="insert into checker (UserName,Pin,Aid) values (?,?,?);";
            int Aid=r.getID();
            String Aholder=r.getholder();
            String branch=r.getbranch();
            System.out.println("Enter the Deposit amount:");
            int deposit=sc.nextInt();
            try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1,Aid);
                stmt.setString(2,Aholder);
                stmt.setString(3,branch);
                stmt.setInt(4,deposit);
                stmt.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            System.out.println("Your details are added Successfully");
            System.out.println("your A/c no.: "+Aid);
            System.out.println("Set Username and Pin to secure your Account");
            String name=r.getUser();
            int pass=r.getPin();
            try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement pstmt = connection.prepareStatement(sql2)){
                pstmt.setString(1,name);
                pstmt.setInt(2,pass);
                pstmt.setInt(3,Aid);
                pstmt.executeUpdate();
                System.out.println("Username and Password added successfully.");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }
    else if(option==2){
        int Anum=r.getdetails();
        String sql="select Abalance from Interface where Aid= ?;";
        int withdraw;
        if(Anum!=0){
        try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,Anum);
            try(ResultSet r1=stmt.executeQuery()){
                while(r1.next()){
                    int bal=r1.getInt("Abalance");
                    System.out.println("Enter Withdrawl Amount(balance: "+bal+"): ");
                    withdraw=sc.nextInt();
                    if(withdraw<=bal){
                        int left=bal-withdraw;
                        String sql3="update Interface set Abalance= ? where Aid= ?;";
                        try(PreparedStatement stmt1 = connection.prepareStatement(sql3)){
                            stmt1.setInt(1,left);
                            stmt1.setInt(2,Anum);
                            stmt1.executeUpdate();
                            System.out.println("Collect Cash");
                            String sql4="update Interface set History=\"Withdraw: "+String.valueOf(withdraw)+"\""+" where Aid="+String.valueOf(Anum)+";";
                            try(Statement statement=connection.createStatement()){
                                statement.executeUpdate(sql4);
                            }
                            catch(SQLException e){
                                e.printStackTrace();
                            }
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                            }
                    }
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
    else{
        System.out.println("Inavlid Case\nTransaction Terminated");
    }
}
    else if(option==3){
        int Anum=r.getdetails();
        String sql="select Abalance from Interface where Aid= ?;";
        int deposit;
        if(Anum!=0){
        try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,Anum);
            try(ResultSet r1=pstmt.executeQuery()){
                while(r1.next()){
                    int bal=r1.getInt("Abalance");
                    System.out.println("Enter Deposit Amount(bal:"+String.valueOf(bal)+"):");
                    deposit=sc.nextInt();
                    int total=bal+deposit;
                    String sql3="update Interface set Abalance= ? where Aid= ?;";
                    try(PreparedStatement stmt1 = connection.prepareStatement(sql3)){
                        stmt1.setInt(1,total);
                        stmt1.setInt(2,Anum);
                        stmt1.executeUpdate();
                        System.out.println("Cash Successfully Deposited");
                        String sql4="update Interface set History=\"Deposit: "+String.valueOf(deposit)+"\""+" where Aid="+String.valueOf(Anum)+";";
                        try(Statement statement=connection.createStatement()){
                            statement.executeUpdate(sql4);
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                        }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }
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
else{
    System.out.println("Invalid Case\nTransaction Terminated");
}
}
else if(option==4){
    int Anum=r.getdetails();
    int transfer;
    String sql="select Abalance from Interface where Aid= ?;";
    if(Anum!=0){
        try(Connection connection=DriverManager.getConnection(url, User, Pass);PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,Anum);
                            try(ResultSet r1=stmt.executeQuery()){
                                while(r1.next()){
                                    int bal=r1.getInt("Abalance");
                                    System.out.println("Enter Transfer Amount(bal:"+String.valueOf(bal)+"):");
                                    transfer=sc.nextInt();
                                    int total=bal-transfer;
                                    String sql3="update Interface set Abalance= ? where Aid= ?;";
                                    try(PreparedStatement stmt1 = connection.prepareStatement(sql3)){
                                        stmt1.setInt(1,total);
                                        stmt1.setInt(2,Anum);
                                        stmt1.executeUpdate();
                                        String sql4="update Interface set History=\"Transfered: "+String.valueOf(transfer)+"\" where Aid= "+String.valueOf(Anum)+";";
                                        try(Statement statement=connection.createStatement()){
                                            statement.executeUpdate(sql4);
                                        }
                                        catch(SQLException e){
                                            e.printStackTrace();
                                        }
                                        String sql5="select Abalance from Interface where Aid= ?;";
                                        System.out.println("Enter Reciever Account no.");
                                        int transferid=sc.nextInt();
                                        try(PreparedStatement stmt2=connection.prepareStatement(sql5)){
                                            stmt2.setInt(1,transferid);
                                            try(ResultSet r2=stmt2.executeQuery()){
                                                while(r2.next()){
                                                    int tbal=r2.getInt("Abalance");
                                                    int ubal=transfer+tbal;
                                                    String sql6="update Interface set Abalance= ? where Aid= ?;";
                                                    try(PreparedStatement stmt3=connection.prepareStatement(sql6)){
                                                      stmt3.setInt(1,ubal);
                                                      stmt3.setInt(2,transferid);
                                                      stmt3.executeUpdate();
                                                      System.out.println("Transfer Successfull");                                            
                                                      String sql7="update Interface set History=\"Recieved: "+String.valueOf(transfer)+"\" where Aid= "+String.valueOf(transferid)+";";;
                                                      try(Statement stmt4=connection.createStatement()){
                                                        stmt4.executeUpdate(sql7);
                                                      }
                                                      catch(SQLException e){
                                                        e.printStackTrace();
                                                      }
                                                    }
                                                    catch(SQLException e){
                                                        e.printStackTrace();
                                                      }
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
                                catch(SQLException e){
                                    e.printStackTrace();
                                }
                                
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
        
    else{
        System.out.println("Invalid Case\nTransaction Terminated");
    }

}
    else if(option==5){
        r.display();
        
    }
    else{
        System.out.println("Thanks for using ABC Bank");
        break;
    }
}

sc.close();       
    
}
}
