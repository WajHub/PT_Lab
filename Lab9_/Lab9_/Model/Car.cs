using System.Xml.Serialization;

namespace MyApp.Model;

[XmlType("car")]
public class Car
{
    public string model { get; set; }
    public int year { get; set; }
    
    [XmlElement(ElementName = "engine")]
    public Engine motor { get; set; }

    public Car(string model, Engine motor, int year)
    {
        this.model = model;
        this.year = year;
        this.motor = motor;
    }

    public Car()
    {
    }

    public override string ToString()
    {
        return $"Model: {model}, Year: {year}, Motor: \n     {motor}";
    }
    
}