using System.Xml.Serialization;

namespace MyApp.Model;

public class Engine
{
    public double displacement { get; set; }
    public double horsePower { get; set; }
    [XmlAttribute]
    public string model { get; set; }

    public Engine(double displacement, double horsePower, string model)
    {
        this.displacement = displacement;
        this.horsePower = horsePower;
        this.model = model;
    }

    public Engine()
    {
    }

    public override string ToString()
    {
        return $"Model: {model}, horsePower: {horsePower}, displacement: {displacement}";
    }
}