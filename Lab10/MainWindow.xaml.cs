using System.Collections.Generic;
using System;
using System.Linq; // Added using System.Linq
using System.Windows;
using System.Windows.Controls;

namespace Lab10
{
    public partial class MainWindow : Window
    {
        public static List<Car> myCars = new List<Car>
        {
            new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
            new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
            new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
            new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
            new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
            new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
            new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
            new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
            new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
        };

        public MainWindow()
        {
            InitializeComponent();
            // Ex1 
            ex1_1();
            ex1_2();

            // Ex2
            Ex2();

            // Ex3-4
            ComboBox.Items.Add("Model");
            ComboBox.Items.Add("Motor");
            ComboBox.Items.Add("Year");
        }

        private static void ex1_1()
        {
            try
            {
                var result = from car in myCars
                             where car.Model == "A6"
                             let engineType = car.Motor.Model == "TDI" ? "diesel" : "petrol"
                             let hppl = (double)car.Motor.Horsepower / car.Motor.Displacement
                             group hppl by engineType into g
                             orderby g.Average() descending
                             select new
                             {
                                 engineType = g.Key,
                                 avgHPPL = g.Average()
                             };

                var output = result.Aggregate("Ex 1.1 (using query expression syntax) \n", (current, e) => current + (e.engineType + ": " + e.avgHPPL + " \n"));
                MessageBox.Show(output);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Err");
            }
        }

        private static void ex1_2()
        {
            try
            {
                var result = myCars
                    .Where(car => car.Model == "A6")
                    .Select(car => new
                    {
                        engineType = car.Motor.Model == "TDI" ? "diesel" : "petrol",
                        hppl = (double)car.Motor.Horsepower / car.Motor.Displacement
                    })
                    .GroupBy(car => car.engineType)
                    .Select(g => new
                    {
                        engineType = g.Key,
                        avgHPPL = g.Average(c => c.hppl)
                    })
                    .OrderByDescending(c => c.avgHPPL);

                var output = result.Aggregate("Ex 1.2 (using method-based query syntax) \n", (current, e) => current + (e.engineType + ": " + e.avgHPPL + " \n"));
                MessageBox.Show(output);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Err");
            }
        }

        private static void Ex2()
        {
            Comparison<Car> arg1 = delegate (Car x, Car y)
            {
                return y.Motor.Horsepower.CompareTo(x.Motor.Horsepower);
            };

            Predicate<Car> arg2 = delegate (Car car)
            {
                return car.Motor.Model == "TDI";
            };

            Action<Car> arg3 = delegate (Car car)
            {
                MessageBox.Show("Ex2. Result: \n Model: " + car.Model + " Silnik: " + car.Motor + " Rok: " + car.Year);
            };
            myCars.FindAll(arg2).ForEach(arg3);
        }

    
    }

    public class Car
    {
        public string Model { get; set; }
        public Engine Motor { get; set; }
        public int Year { get; set; }

        public Car(string model, Engine motor, int year)
        {
            Model = model;
            Motor = motor;
            Year = year;
        }
    }

    public class Engine : IComparable
    {
        public string Model { get; set; }
        public double Horsepower { get; set; }
        public double Displacement { get; set; }

        public Engine(double displacement, double horsepower, string model)
        {
            Model = model;
            Horsepower = horsepower;
            Displacement = displacement;
        }
        public Engine()
        {
        }

        public int CompareTo(object obj)
        {
            return Horsepower.CompareTo(obj);
        }

        public override string ToString()
        {
            return $"{Model},  {Horsepower}, {Displacement}";
        }

    }
}
