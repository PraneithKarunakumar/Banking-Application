
package coe528.project;

import static java.awt.SystemColor.window;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author praneith Karunakumar
 */

public class Banking extends Application {
    manager Admin = new manager("admin","admin","manager");
    customer Customer = null;
    Stage Login;
    Stage AdminAcess;
    Stage AdminEdit;
    Stage AdminAdd;
    Stage AdminDelete;
    Stage CustomerAcess;
    Stage shopeScene;
    int amount = 0;
    int total = 0;
    
    DecimalFormat df = new DecimalFormat("#.##");
    
    public static void main(String[] args) {   
        launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        Text result = new Text();
        Login=primaryStage;
       
        Login.setTitle("bank login");
        
        GridPane grid= new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        
        Text welcomeTxt=new Text("enter your banking info");
        grid.add(welcomeTxt,0 ,0 );
        
        Label userIn = new Label("user");
        grid.add(userIn,0 ,1 );
        
        TextField user = new TextField();
        user.setPromptText("enter User");
        grid.add(user,1 ,1 );
        
        Label pasIn = new Label("password");
        grid.add(pasIn,0 ,2 );
        
        PasswordField pass = new PasswordField();
        pass.setPromptText("enter password");
        grid.add(pass,1 ,2 );
        
        Button login = new Button("login");
        grid.add(login,1,3);
        
        
        grid.add(result,1,4);
        
        
        Scene scene= new Scene(grid,600,600);
        Login.setScene(scene);
        Login.show();
        login.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String userName = user.getText();
                String password = pass.getText();
                Identify(userName, password, primaryStage,result);
            }
        });
        
    }

    
    public void Identify(String name, String pass, Stage initialStage,Text result){
       
        File locate = new File(name + ".txt");
        if(name.equals("admin") && pass.equals("admin")){ //check is it owner?
                Login.close();
                managerScene(initialStage);   //owner main screen  
        }
        else{
            if (locate.exists()) {
                try{
                    Scanner myReader = new Scanner(locate);
                    String data = myReader.nextLine();
                    System.out.println(data);
                    String[] parts = data.split("\\s+");
                    String userName = parts[0];
                    String Password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    
                    if(Password.equals(pass)){
                        Customer = new customer(userName, Password,"customer",balance);
                        Login.close();
                        CustomerScene(initialStage);
                    }
                    else{
                        result.setText("incorrect password");
                        System.out.println("incorrect password");
                    }
                } 
                catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                
            } 
            else{
               result.setText("user name not found");
               System.out.println("no person by that name found"); 
            }
        }
    }
    
    
    
    
    public void CustomerScene(Stage primaryStage){
        Text userName = new Text();
        Text role = new Text();
        Text balance = new Text();
        Text level = new Text();
        Text withdrawResult = new Text();
        Text depositResult = new Text();
        CustomerAcess=primaryStage;
        CustomerAcess.setTitle("customer");
        GridPane grid3= new GridPane();
        Scene scene= new Scene(grid3,600,600);
        grid3.setAlignment(Pos.CENTER);
        grid3.setVgap(10);
        grid3.setHgap(10);
        
        Text welcomeTxt=new Text("Hello you can do the following");
        grid3.add(welcomeTxt,0,0);
        userName.setText("username: "+ Customer.getUser());
        role.setText("role: "+ Customer.getRole());
        balance.setText("balance: $"+ df.format(Customer.getBalance()));
        level.setText("level: "+ Customer.getLevel());
        grid3.add(userName,5,1);
        grid3.add(role,5,2);
        grid3.add(balance,5,3);
        grid3.add(level, 5, 4);
        grid3.add(withdrawResult,1 ,3 );
        grid3.add(depositResult,0 ,3 );
        
        
        Button deposit = new Button("deposit");
        grid3.add(deposit,0,2);
        
        TextField depositAmount = new TextField();
        depositAmount.setPromptText("enter deposite amount");
        grid3.add(depositAmount,0 ,1 );
        
        
        Button withdraw = new Button("withdraw");
        grid3.add(withdraw,1,2);
        
        TextField withdrawAmount = new TextField();
        withdrawAmount.setPromptText("enter withdraw amount");
        grid3.add(withdrawAmount,1 ,1 );
        
        
        Button shop= new Button("shop");
        grid3.add(shop,1,4);
        
        
        Button logout = new Button("logout");
        grid3.add(logout,0,4);
        
        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int m=0;
                for (int i = 0; i < withdrawAmount.getText().length(); i++){
                    if (Character.isDigit(withdrawAmount.getText().charAt(i)) == false && withdrawAmount.getText().charAt(i) != '.'){
                        m++;
                    }
                }
                if(m==0 && 0<=Double.parseDouble(withdrawAmount.getText()) && 0<= Customer.getBalance() - Double.parseDouble(withdrawAmount.getText())){
                    withdrawResult.setText("withdraw was successful");
                    Customer.ChangeBalance(Customer.getBalance() - Double.parseDouble(withdrawAmount.getText()));
                    balance.setText( "balance: $" + df.format(Customer.getBalance()) );
                    level.setText("level: "+ Customer.getLevel());
                    String customerInfo = Customer.getUser() + " " + Customer.getPass() + " " + Customer.getBalance();
                    Customer.SaveData(customerInfo, Customer.getUser());
                    withdrawAmount.clear();
                }
                else{
                   withdrawResult.setText("incorrect value you put in.");
                }
            }
        });
        
        deposit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int m=0;
                for (int i = 0; i < depositAmount.getText().length(); i++){
                    if (Character.isDigit(depositAmount.getText().charAt(i)) == false && depositAmount.getText().charAt(i) != '.'){
                        m++;
                    }
                }
                if(m==0 && 0<=Double.parseDouble(depositAmount.getText()) && 0<= Customer.getBalance() + Double.parseDouble(depositAmount.getText())){
                    depositResult.setText("deposit was successful");
                    Customer.ChangeBalance(Customer.getBalance() + Double.parseDouble(depositAmount.getText()));
                    balance.setText( "balance: $" + df.format( Customer.getBalance()) );
                    level.setText("level: "+ Customer.getLevel());
                    String customerInfo = Customer.getUser() + " " + Customer.getPass() + " " + Customer.getBalance();
                    Customer.SaveData(customerInfo, Customer.getUser());
                    depositAmount.clear();
                }
                else{
                   depositResult.setText("incorrect value you put in.");
                }
            }
        });
        
        
        shop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                CustomerAcess.close();
                shop(primaryStage);
            }
        });
        
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                CustomerAcess.close();
                start(primaryStage);
            }
        });
          
        CustomerAcess.setScene(scene);
        CustomerAcess.show();
    }
    
    public void shop(Stage primaryStage){
        
        shopeScene=primaryStage;
        Text item1 = new Text();
        Text item2 = new Text();
        Text item3 = new Text();
        Text amountYouHave = new Text();
        Text amountSelected = new Text();
        Text amountOwed = new Text();
        Text yourLevel = new Text();
        Text fee = new Text();
        GridPane grid5= new GridPane();
        Scene scene= new Scene(grid5,600,600);
        grid5.setAlignment(Pos.CENTER);
        grid5.setVgap(10);
        grid5.setHgap(10);
        item1.setText("Headphones cost: $15");
        item2.setText("Notebook cost: $10");
        item3.setText("Calculator cost: $5");
        //amountOwed.setText("Amount Owed: $"+ (amount+Customer.getfee()));
        amountSelected.setText("Selected Items Cost: $"+amount);
        
        amountYouHave.setText( "balance: $" + df.format(Customer.getBalance()) );
        yourLevel.setText("Level: "+Customer.getLevel());
        fee.setText("Fee: $"+Customer.getfee());
        grid5.add(item1, 0, 0);
        grid5.add(item2, 0, 1);
        grid5.add(item3, 0, 2);
        grid5.add(amountYouHave, 2, 3);
        grid5.add(amountSelected, 0, 3);
        grid5.add(fee, 0, 4);
        grid5.add(yourLevel, 2, 4);
        grid5.add(amountOwed, 0, 5);
        Text result = new Text();
        grid5.add(result, 0, 6);
        
        Button item1Add = new Button("add");
        item1Add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
             
                result.setText("");
                amount =amount +15;
                amountSelected.setText("Selected Items Cost: $"+amount);
                total = amount + Customer.getfee();
                amountOwed.setText("total: $"+ total);
                fee.setText("Fee: $"+Customer.getfee());
            }
        });
        
        
        Button item2Add = new Button("add");
        item2Add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                result.setText("");
                amount =amount +10;
                amountSelected.setText("Selected Items Cost: $"+amount);
                total = amount + Customer.getfee();
                amountOwed.setText("total: $"+ total);
                fee.setText("Fee: $"+Customer.getfee());

            }
        });
        
        Button item3Add = new Button("add");
        
        item3Add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                result.setText("");
                amount =amount +5;
                amountSelected.setText("Selected Items Cost: $"+amount);
                total = amount + Customer.getfee();
                amountOwed.setText("total: $"+ total);
                fee.setText("Fee: $"+Customer.getfee());
            }
        });
        
        Button item1Remove = new Button("remove");
        item1Remove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                amount =amount -15;
                if(amount ==0 ){
                    amount = 0;
                    total=0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("have nothing in cart");
                } 
                else if(amount >0){
                    result.setText("");
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    fee.setText("Fee: $"+Customer.getfee());
                    total = amount + Customer.getfee();
                    amountOwed.setText("total: $"+ total);
                }
                else{
                    amount = 0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("you have removed all items!");
                }
            }
        });
        
        Button item2Remove = new Button("remove");
        item2Remove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                amount =amount -10;
                if(amount ==0 ){
                    amount = 0;
                    total=0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("have nothing in cart");
                } 
                else if(amount >0){
                    result.setText("");
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    fee.setText("Fee: $"+Customer.getfee());
                    total = amount + Customer.getfee();
                    amountOwed.setText("total: $"+ total);
                }
                else{
                    amount = 0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("you have removed all items!");
                }
            }
        });
        
        Button item3Remove = new Button("remove");
        item3Remove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                amount =amount - 5;
                if(amount ==0 ){
                    amount = 0;
                    total=0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("have nothing in cart");
                } 
                else if(amount >0){
                    result.setText("");
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    fee.setText("Fee: $"+Customer.getfee());
                    total = amount + Customer.getfee();
                    amountOwed.setText("total: $"+ total);
                }
                else{
                    amount = 0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    amountOwed.setText("");
                    result.setText("you have removed all items!");
                }
            }
        });
        
        Button purchase = new Button("purchase");
        purchase.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(total ==0){
                    result.setText("have nothing in cart to purchase");
                } 
                else if(total >=50 && Customer.getBalance() >= total){
                    amount=0;
                    amountSelected.setText("Selected Items Cost: $"+amount);
                    fee.setText("Fee: $"+Customer.getfee());
                    amountOwed.setText("");
                    Customer.ChangeBalance(Customer.getBalance()-total);
                    total = 0;
                    amountYouHave.setText( "balance: $" + df.format(Customer.getBalance()) );
                    yourLevel.setText("Level: "+Customer.getLevel());
                    String customerInfo = Customer.getUser() + " " + Customer.getPass() + " " + Customer.getBalance();
                    Customer.SaveData(customerInfo, Customer.getUser());
                    result.setText(" purchase successful");
                }
                else{
                    result.setText("lack of funds or not above $50...");
                }
            }
        });
        
        
        grid5.add(purchase,2,5);
        grid5.add(item1Add,1,0);
        grid5.add(item1Remove,2,0);
        grid5.add(item2Add,1,1);
        grid5.add(item2Remove,2,1);
        grid5.add(item3Add,1,2);
        grid5.add(item3Remove,2,2);
        
        Button goBack = new Button("goBack");
        grid5.add(goBack,2,6);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                amount=0;
                total=0;
                shopeScene.close();
                CustomerScene(primaryStage);
            }
        });
        
        shopeScene.setScene(scene);
        shopeScene.show();
    }
    
    public void managerScene(Stage primaryStage){
        AdminAcess=primaryStage;
        AdminAcess.setTitle("Manager");
        GridPane grid1= new GridPane();
        Scene scene= new Scene(grid1,600,600);
        grid1.setAlignment(Pos.CENTER);
        grid1.setVgap(10);
        grid1.setHgap(10);
        
        Text welcomeTxt=new Text("Hello you can do the following:");
        grid1.add(welcomeTxt,0,2);
        
        Text info=new Text("balance: "+ Admin.getBalance());
        grid1.add(info,2,2);
        
        Text info1=new Text("Role: "+ Admin.getRole());
        grid1.add(info1,2,3);
        
        Button add = new Button("add Account");
        grid1.add(add,0,3);
        
        Button delete = new Button("delete Account");
        grid1.add(delete,0,4);
        
        Button logout = new Button("logout");
        grid1.add(logout,2,4);
        
        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                AdminAcess.close();
                AddScene(primaryStage);
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                AdminAcess.close();
                DeleteScene(primaryStage);
            }
        });
        
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                AdminAcess.close();
                start(primaryStage);
            }
        });
        AdminAcess.setScene(scene);
        AdminAcess.show();
    }
    public void AddScene(Stage primaryStage) {
        Text state = new Text();
        AdminAdd = primaryStage;
        AdminAdd.setTitle("Manager Add");
        GridPane grid2 = new GridPane();
        Scene scene = new Scene(grid2, 600, 600);
        grid2.setAlignment(Pos.CENTER);
        grid2.setVgap(10);
        grid2.setHgap(10);
        grid2.add(state,1 ,4 );
        Label customerUser = new Label("enter username:");
        grid2.add(customerUser,0 ,1 );
        
        TextField customerUsername = new TextField();
        customerUsername.setPromptText("enter User");
        grid2.add(customerUsername,1 ,1 );
        
        Label customerPass = new Label("password");
        grid2.add(customerPass,0 ,2 );
        
        TextField customerPassword = new TextField();
        customerPassword.setPromptText("enter password:");
        grid2.add(customerPassword,1 ,2 );
        
        Button addCustomerBtn = new Button("Add Customer");
       
        grid2.add(addCustomerBtn, 1, 3);
        addCustomerBtn.setOnAction(event -> {
            String CustomerUserID = customerUsername.getText();
            String Customerpassword = customerPassword.getText();
            Double CustomerBalance = 100.0;
            Admin.addCustomer(CustomerUserID,Customerpassword,CustomerBalance,state);
        });
        
        Button backBtn = new Button("Back to Manager Screen");
        grid2.add(backBtn, 0, 3);
        backBtn.setOnAction(event -> {
            AdminAdd.close();
            managerScene(primaryStage);
        });
        
        
        AdminAdd.setScene(scene);
        AdminAdd.show();
        
    }
    
    public void DeleteScene(Stage primaryStage) {
        Text state = new Text();
        AdminDelete = primaryStage;
        AdminDelete.setTitle("Manager Delete");
        GridPane grid6 = new GridPane();
        Scene scene = new Scene(grid6, 600, 600);
        grid6.setAlignment(Pos.CENTER);
        grid6.setVgap(10);
        grid6.setHgap(10);
        grid6.add(state,1 ,4 );
        Label customerUser = new Label("username:");
        grid6.add(customerUser,0 ,1 );

        TextField customerUsername = new TextField();
        customerUsername.setPromptText("enter User");
        grid6.add(customerUsername,1 ,1 );

        Button DeleteCustomerBtn = new Button("Delete Customer");

        grid6.add(DeleteCustomerBtn, 1, 2);
        DeleteCustomerBtn.setOnAction(event -> {
            String CustomerUserID = customerUsername.getText();
            Double CustomerBalance = 100.0;
            //System.out.println(""+CustomerBalance);
            Admin.deleteCustomer(CustomerUserID,CustomerBalance,state);
        });

        Button backBtn = new Button("Back to Manager Screen");
        grid6.add(backBtn, 0, 2);
        backBtn.setOnAction(event -> {
            AdminDelete.close();
            managerScene(primaryStage);
        });

        AdminDelete.setScene(scene);
        AdminDelete.show();
        
        
    }
}