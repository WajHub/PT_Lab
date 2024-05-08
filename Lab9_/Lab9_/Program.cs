using System;
using System.Text.RegularExpressions;
using System.Xml;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.XPath;
using MyApp.Model;

namespace MyApp // Note: actual namespace depends on the project name.
{
    internal class Program
    {
        public static List<Car> myCars = new List<Car>(){
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

        public static void ex1()
        {
            var selected =
                from car in myCars
                where (car.model == "A6")
                select new
                {
                    car,
                    engineType = car.motor.model=="TDI" ? "Diesel" : "petrol",
                    hppl = car.motor.horsePower / car.motor.displacement
                };
            Console.WriteLine("Cars with model A6: \n");
            foreach (var car in selected)
            {
                Console.WriteLine(car);
            }

            var groupedCars = selected.GroupBy(car => car.engineType).
                    Select(res => new
                    {
                        engineType = res.Key,
                        avg = res.Average(arg => arg.hppl)
                    });
            Console.WriteLine("\n \n AVG for diesel and petrol: \n");
            foreach (var result in groupedCars)
            {
                Console.WriteLine($"{result.engineType}: {result.avg}");
            }

        }

        public static void serialize()
        {
            XmlSerializer xmlSerializer = new XmlSerializer(typeof(List<Car>), new XmlRootAttribute("cars"));
            var path = Directory.GetCurrentDirectory()+"../../../../cars.xml";
            TextWriter writer = new StreamWriter(path);
            xmlSerializer.Serialize(writer, myCars);
            Console.WriteLine("\n Serialized in cars.xml \n");
        }

        public static List<Car> deserialize()
        {
            XmlSerializer xmlSerializer = new XmlSerializer(typeof(List<Car>), new XmlRootAttribute("cars"));
            var path = Directory.GetCurrentDirectory()+"../../../../cars.xml";
            using (XmlReader reader = XmlReader.Create(path))
            {
                return (List<Car>) xmlSerializer.Deserialize(reader);
            }
        }

        public static void ex3()
        {
            var path = Directory.GetCurrentDirectory()+"../../../../cars.xml";
            XElement rootNode = XElement.Load(path);
            double avgHP = (double) rootNode.XPathEvaluate("sum(//car/engine[@model!=\"TDI\"]/horsePower) div count(//car/engine[@model!=\"TDI\"]/horsePower)");
            
            Console.WriteLine("AVG HP:"+avgHP);
            
            IEnumerable<XElement> models = rootNode.XPathSelectElements(
                "//car/engine[@model and not(@model = preceding::car/engine/@model)]");
            Console.WriteLine("\n Models");
            foreach (var model in models)
            {
                Console.WriteLine(model.Attribute("model")?.Value);
            }
        }

        private static void createXmlFromLinq()
        {
            IEnumerable<XElement> nodes = Program.myCars.Select(car =>
                new XElement("car",
                    new XElement("model", car.model),
                    new XElement("year", car.year),
                    new XElement("engine", 
                        new XAttribute("model", car.motor.model),
                        new XElement("displacement", car.motor.displacement),
                        new XElement("horsePower", car.motor.horsePower)
                        ))
            );
                
            XElement rootNode = new XElement("cars", nodes); 
            rootNode.Save(Directory.GetCurrentDirectory()+"../../../../carsLINQ.xml");
        }

        private static void xmlToXhtml()
        {
            XNamespace xhtml = "http://www.w3.org/1999/xhtml";

            var cars = myCars.Select(car =>
                new XElement("tr",
                    new XElement("td", car.model),
                    new XElement("td", car.year),
                    new XElement("td", car.motor.displacement),
                    new XElement("td", car.motor.horsePower),
                    new XElement("td", car.motor.model))
            );
                
            var elements = new XElement(
                xhtml + "link",
                new XElement("table",
                    cars)
            );
            var html = XElement.Load("../../../template.html");
            var body = html.Element("{http://www.w3.org/1999/xhtml}body");
            body?.Add(elements);
            html.Save("../../../newTemplate.html");
        }

        private static void ex6()
        {
            var path = Directory.GetCurrentDirectory()+"../../../../cars.xml";
            XDocument doc = XDocument.Load(path);
            
            foreach (var root in doc.Elements())
            {
                foreach (var car in root.Elements())
                {
                    XElement year = new XElement("year", "year");
                    foreach (var elementCar in car.Elements())
                    {
                        if (elementCar.Name == "year")
                        {
                            year = elementCar;
                            elementCar.Remove();
                        }
                        if (elementCar.Name == "engine")
                        {
                            foreach (var engine in elementCar.Elements())
                            {
                                if (engine.Name == "horsePower")
                                {
                                    engine.Name = "hp";
                                }
                            }
                        }
                    }

                    foreach (var elementCar in car.Elements())
                    {
                        if (elementCar.Name == "model")
                        {
                            elementCar.SetAttributeValue("year", year.Value);
                            
                        }
                    }

                }

                doc.Save("../../../newCars.xml");
            }
        }
        
        static void Main(string[] args)
        {
            
            ex1();
            serialize();
            List<Car> cars = deserialize();
            Console.WriteLine("\n\n Deserialized objects\n");
            foreach (var car in cars)  Console.WriteLine(car);
            ex3();
            createXmlFromLinq();
            xmlToXhtml();
            ex6();


        }
    }
}