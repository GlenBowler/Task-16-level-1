

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class QuickFood{
	
	//Main
	public static void main(String[] args) {
		//Accessing my customer details
		Customer newCustomer=CustomerDetails();
		//Accessing my resturant details
		Resturant BurgerJoint=createResturant();
		//Customer adress
		String customerAdress=newCustomer.getCustomerAdress();
		//Customer Location
		String customerLocation=newCustomer.getCustomerCity();
		//Resturant location
		String resturantLocation=BurgerJoint.getLocationOfResturant();
		//Getting customer Name
		String customerName=newCustomer.getCustomerFullname();
		//Getting customer orderNumber
		String customerOrderNum=newCustomer.getCustomerOrderNumber();
		//Creating a set with all the cities in country where my resturant is
		Set<String> cities=new HashSet<String>();
		cities.add("Cape Town");
		cities.add("Durban");
		cities.add("Johannesburg");
		cities.add("Potchefstroom");
		cities.add("Springbok");
		cities.add("Bloemfontein");
		cities.add("Port Elizabeth");
		cities.add("Witbank");
		
	if(cities.contains(customerLocation)) {
		try {
			//Getting driver list
			File myDrivers=new File("drivers.txt");
			Scanner readDriver=new Scanner(myDrivers);
			//Array to store all valid drivers inside
			ArrayList<String> validDrivers=new ArrayList<>();
			
			//While the text file of drivers got a next Line we will run this loop to store drivers in arr
			while(readDriver.hasNextLine()) {
				//read line from driver text file
				String fileInput=readDriver.nextLine();
				//Splitting driver details up in 3 part arr
				String[] driverList=fileInput.split(", ");
				
				//if statement to see what driver is going to be stored as valid driver 
				if(driverList[1].equals(customerLocation)) {
					validDrivers.add(fileInput);
				}
			}
			
			int lowestAmount=1000;//making it large number since know no one would have more than 1000 d
			String driverToDoLoad = null;
			//For loop that will determine the driver with the least loads of all your drivers
			for(int j=0;j<validDrivers.size();j++) {
				//Getting the first driver that we want to compare and split with ,
				String[] driver=validDrivers.get(j).split(", ");
				//Getting driver amount of loads that he/she is busy with 
				int driverAmountOfLoads=Integer.parseInt(driver[2]);
				//for loop to check other drivers in valid drivers and compare to first driver
				if(driverAmountOfLoads<lowestAmount) {
					//Assigning the new lowestValue
					lowestAmount=driverAmountOfLoads;
					//Saving driver name with lowest trips
					driverToDoLoad=validDrivers.get(j);
				}
			}
		
			//Saving the driver to do load inside array so can only get the driver name
			String[] driverArray=driverToDoLoad.split(", ");
			//Getting driver name
			String driverName=driverArray[0];
			//Assigning the new driver to my class
			Driver myDriver=new Driver(driverName,customerAdress);
			//Calling function to write out you customer name and ordernumber
			customerText(customerOrderNum,customerName);
			//Calling function to write out customer name and location
			customerLocationsGrouped(customerName,customerLocation);
			//Adding to driver trips
			addDriverTrips(driverToDoLoad);
	  }
		
		//catch exception for if the drivers.txt file is not found
		catch (FileNotFoundException e) {
			System.out.println("Error");
			}
		
		try {
			PrintWriter writer=new PrintWriter("invoice.txt","UTF-8");
			writer.write(newCustomer.displayCustomer());
			writer.write(BurgerJointMenu());
			writer.write(Driver.displayDriver());
			writer.write(createResturant().displayResturant());
			writer.close();
		}
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
		
		//if cutomer location and resturant location does not equal each other return this message
		else {
			try {//Write to invoice if we dont have someone there
				PrintWriter writer=new PrintWriter("invoice.txt","UTF-8");
				writer.write("Sorry! Our drivers are to far away from you to be able to deliver to your location");
				writer.close();
		}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//Creating a resturant
	public static Resturant createResturant() {
		//Assigning valaus to my new Resturant
		String name="Burger Joint";
		String resturantLocation="Cape Town";
		String number="072 222 4897";
		Resturant BurgerJoint=new Resturant(name,resturantLocation,number);
		return BurgerJoint;
	}
	
	//Function to get user order
	public static String BurgerJointMenu(){
		//Prices for burgers
		int singeCheesePrice=48;
		int doubleCheesePrice=79;
		int megaCheesPrice=100;
		
		//Counter to check how much of each item user ordered
		int countSingle=0;
		int countDouble=0;
		int countMega=0;
		
		//Total prices
		int totalSingle=0;
		int totalDouble=0;
		int totalMega=0;
		int totalPrice=0;
		//Asking user what he want
		Scanner input=new Scanner(System.in);
		Scanner specail=new Scanner(System.in);
		int myChoice =1;
		
		do {
			System.out.println("Please enter the number you want to order:"
					+ "\n 1:Single Cheese Burger (R48.00)"
					+ "\n 2:Double cheese burger (R79.00)"
					+ "\n 3:Mega cheese burger (R100.00)"
					+ "\n 4:I am done ordering");
			myChoice=input.nextInt();
			
			if(myChoice ==1) {
				countSingle++;
			}
			else if(myChoice ==2) {
				countDouble++;
			}
			else if(myChoice ==3) {
				countMega++;
			}
		}
		
		while (myChoice!=4) ;
			//Specail instructions
			System.out.println("Is there any specail instructions you want to add");
			String specailInstructions=specail.nextLine();
			//Calculation for the user total order
			totalSingle=countSingle*singeCheesePrice;
			totalDouble=countDouble*doubleCheesePrice;
			totalMega=countMega*megaCheesPrice;
			totalPrice=totalSingle+totalDouble+totalMega;
			//Return value that we will store on file
			String myOrder="Your oder is as follow:"
			+"\n\n"+countSingle+"X"+" Single Cheese burger"+"(R"+totalSingle+")"
			+"\n"+countDouble+"X"+" Double Cheese burger"+"(R"+totalDouble+")"
			+"\n"+countMega+"X"+" Mega Cheese burger"+"(R"+totalMega+")"
			+ "\n\nSpecial Instructions:"+specailInstructions
			+"\n\nTotal: R"+totalPrice+"\n\n";
		
		return myOrder;
}

	//Geting the user info
	public static Customer CustomerDetails() {
		Scanner input=new Scanner(System.in);
		//Order number
		int [] orderNum=new int[4];
		int max=10;
		int min=1;
		for (int i=0;i<orderNum.length;i++) {//Generating random valuse for your order number
			orderNum[i]=(int) (Math.random()*((max-min+1)+min));
			}
		String customerOrderNumber=Integer.toString(orderNum[0])+Integer.toString(orderNum[1])+Integer.toString(orderNum[2])+Integer.toString(orderNum[3]);//Concacting the integers to string
		//Name
			System.out.println("Please enter your name");
			String customerName=input.nextLine();
		//Surname
			System.out.println("Please enter your surname");
			String customerSurname=input.nextLine();
		//Fullname
			String customerFullName=customerName+" "+customerSurname;	
		//Email adress
			System.out.println("Please enter your email address");
			String customerEmail=input.nextLine();
		//Phone number
			System.out.println("Please enter your phone number");
			String customerPhoneNumber=input.nextLine();
		//Location
			System.out.println("Please enter your city your live in");
			String customerLocation=input.nextLine();
		//Adress
			System.out.println("Please enter your adress");
			String customerAdress=input.nextLine();
			
		//Customer newCustomer=new Customer(customerOrderNumber,customerFullName,customerEmail,customerPhoneNumber,customerLocation);
		Customer newCustomer=new Customer(customerOrderNumber,customerFullName,customerEmail,customerPhoneNumber,customerLocation,customerAdress);	
		return newCustomer;
		}
	
	//creating file so  that we can add customer name and order num to text file
	public static void customerText(String customerOrderNumber,String customerName) {
		try {
			//If text file already exist we can access it
			BufferedWriter oldCustomerDetails = new BufferedWriter(new FileWriter("customerDetails.txt",true));
			oldCustomerDetails.close();
			//Storing our arg into string
			String newCustomer="Customer Name: "+customerName+" Order Number: "+customerOrderNumber;
			//Creating an arraylist to store listOfCustomers
			ArrayList <String> listOfCustomers=new ArrayList<>();
			File customerDetails=new File("customerDetails.txt");
			Scanner sc=new Scanner(customerDetails);
			//while loop to add all the list of customers that was already eneterd before now to an arraylist
			while(sc.hasNextLine()) {
				listOfCustomers.add(sc.nextLine());
			}
			//Add the new customer to list
			listOfCustomers.add(newCustomer);
			sc.close();
			//Sorting the arr
			Collections.sort(listOfCustomers);
			//Creating a new bufferedwriter to overide the prevouis customer details
			BufferedWriter newCustomerDetailsText=new BufferedWriter(new FileWriter("customerDetails.txt"));
			//for loop to write each customer to new file
			for(int i=0;i<listOfCustomers.size();i++) {
				newCustomerDetailsText.write(listOfCustomers.get(i)+"\n");
			}
			newCustomerDetailsText.close();
			//Catch for if something is wrong
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//Creating a file that will store each customer to their location
	public static void customerLocationsGrouped(String customerName,String customerLocation) {
		try {
			//If text file already exist we can access it
			BufferedWriter oldListOfNamesAndTheirLocaiton = new BufferedWriter(new FileWriter("location.txt",true));
			oldListOfNamesAndTheirLocaiton.close();
			//Storing our arg into string
			String newCustomer="Location: "+customerLocation+" Customer Name: "+customerName;
			//Creating an arraylist to store listOfCustomers
			ArrayList <String> listOfLocation=new ArrayList<>();
			File customerDetails=new File("location.txt");
			Scanner sc=new Scanner(customerDetails);
			//while loop to add all the list of customers that was already eneterd before now to an arraylist
			while(sc.hasNextLine()) {
				listOfLocation.add(sc.nextLine());
			}
			//Add the new customer to list and their loaction
			listOfLocation.add(newCustomer);
			sc.close();
			//Sorting the arr
			Collections.sort(listOfLocation);
			//Creating a new bufferedwriter to overide the prevouis customer details
			BufferedWriter newCustomerDetailsText=new BufferedWriter(new FileWriter("location.txt"));
			//for loop to write each customer and their location to new file
			for(int i=0;i<listOfLocation.size();i++) {
				newCustomerDetailsText.write(listOfLocation.get(i)+"\n");
			}
			newCustomerDetailsText.close();
			//Catch for if something is wrong
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//Mehtod that will add drivers trips to text file
	public static void addDriverTrips(String driver) {
		try {
			//Looking for the text file if there we will use it
		BufferedWriter oldDriverText = new BufferedWriter(new FileWriter("drivers.txt",true));
		oldDriverText.close();
		//Spliiting the driver details that will do the load
		String [] driverToDoLoad=driver.split(", ");
		//Acessing the 2nd element inside my arr and changing from string to int so that can add one trip to it
		int driverCurrentLoadsInt=Integer.parseInt(driverToDoLoad[2]);
		driverCurrentLoadsInt+=1;
		//Converting the integer back to 2 string
		String driverCurrentLoad=Integer.toString(driverCurrentLoadsInt);
		//Changing the 2nd value of my driver amount of trips
		driverToDoLoad[2]=driverCurrentLoad;
		
		//Declaring a arralist where all drivers details will be stored
		ArrayList<String> allDriversArr=new ArrayList<>();
		File driverTextFile=new File("C://drivers.txt");
		Scanner sc=new Scanner(driverTextFile);
		//While loop to add all the driver details to arrayList
		while (sc.hasNextLine()) {
			allDriversArr.add(sc.nextLine());
		}
		sc.close();
		//if statement to see if arrayList contains driver name that will do load and deleting all his data
		 if(allDriversArr.contains(driver)) {
			 allDriversArr.remove(driver);
		 }
		 String addMyDriver=driverToDoLoad[0]+", "+driverToDoLoad[1]+", "+driverToDoLoad[2];
		 //Adding driver that doing the load to array after we added new trip to him
		 allDriversArr.add(addMyDriver);
		 //Sorting arraylist so that read easier
		 Collections.sort(allDriversArr);
		 //Creating an arr where all the arraList data would be stored inside 
		 String [] newAllDriversArr=new String [allDriversArr.size()];
		 //Adding arrayList data to Array
		 for(int k=0;k<newAllDriversArr.length;k++) {
			 newAllDriversArr[k]=allDriversArr.get(k);
		 }
		 
		 BufferedWriter updatedText=new BufferedWriter(new FileWriter("drivers.txt"));
		 //Adding the array Data to text file
		 for(int j=0;j<newAllDriversArr.length;j++) {
			 updatedText.write(newAllDriversArr[j]+"\n");
		 }
		 updatedText.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
