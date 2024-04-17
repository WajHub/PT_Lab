using System;
using System.Collections;
using System.IO;
using System.IO.Compression;
using System.Runtime.Serialization.Formatters.Binary;

namespace Lab7
{
    static class Extension
    {
        public static FileInfo? GetOldestFile(this DirectoryInfo directory)
        {
            DateTime oldestDate = DateTime.Now;
            FileInfo? f = null;
            directory.GetFiles().Where(file => file.LastWriteTime < oldestDate);
            foreach (var file in directory.GetFiles().Where(file => file.LastWriteTime < oldestDate))
            {
                    oldestDate = file.LastWriteTime;
                    f = file;  
            }
            foreach (var subDirectory in directory.GetDirectories())
            {
                var oldestSubDirFile = GetOldestFile(subDirectory);
                if (oldestSubDirFile != null && oldestSubDirFile.CreationTimeUtc < oldestDate)
                {
                    f = oldestSubDirFile;
                }
            }
            return f;
        }

        public static string GetAtrDos(this FileSystemInfo fileSystemInfo)
        {
            string result = "";
            result += (fileSystemInfo.Attributes & FileAttributes.ReadOnly) != 0 ? 'R' : '-';
            result += (fileSystemInfo.Attributes & FileAttributes.Archive) != 0 ? 'A' : '-';
            result += (fileSystemInfo.Attributes & FileAttributes.Hidden) != 0 ? 'H' : '-';
            result += (fileSystemInfo.Attributes & FileAttributes.System) != 0 ? 'S' : '-';
            return result;
        }
    }

    [Serializable]
    public class Comparator : IComparer<string>
    {
        public int Compare(string? x, string? y)
        {
    
            if(x.Length > y.Length)
            {
                return 1;
            }
            else if(y.Length > x.Length)
            {
                return -1;
            }
            else
            {
                return string.Compare(x, y, StringComparison.Ordinal);
            }
        }
    }


    internal class Program
    {
        private static void print_files(string path)
        {
            string[] files = Directory.GetFiles(path);
            foreach (string file in files)
            {
                Console.WriteLine(file);
            }
        }

        private static void print_recursive(string path, int depth)
        {
            // print current directory
            Console.Write('d');
            for (int i = 0; i < depth; i++)
            {
                Console.Write(" -- ");
            }
            Console.Write(path);
            int amount = Directory.GetDirectories(path).Length + Directory.GetFiles(path).Length;
            Console.Write("(" + amount+")");
            Console.WriteLine("| Atr: " + Extension.GetAtrDos(new DirectoryInfo(path)));

            // print files
            string[] files = Directory.GetFiles(path);
            foreach (string file in files)
            {
                Console.Write('f');
                for (int i = 0; i < depth; i++)
                {
                    Console.Write(" -- ");
                }
                Console.Write(file);
                Console.Write("| Atr: " + Extension.GetAtrDos(new DirectoryInfo(file)));
                long size = new System.IO.FileInfo(file).Length;
                Console.WriteLine("|"+size+" bajtow");
            }

            string[] directories = Directory.GetDirectories(path);

            foreach (var dir in directories)
            {
                print_recursive(dir, ++depth);
            }



        }


        private static long GetSize(string path)
        {
            string[] directories = null;
            string[] files = null;
            long result = 0;

            if (Directory.Exists(path))
            {
                directories = Directory.GetDirectories(path);
                files = Directory.GetFiles(path);
                foreach (string file in files)
                {
                    result += new System.IO.FileInfo(file).Length;
                }
                foreach (string file in directories)
                {
                    result += GetSize(file);
                }
            }  
                      
            return result;
           
        }




        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("No arguments were passed");
                return;
            }
            print_recursive(args[0], 0);

            // print_files(args[0]);

            FileInfo? fileInfo = Extension.GetOldestFile(new DirectoryInfo(args[0]));
            Console.WriteLine(fileInfo.Name);
            Console.WriteLine(fileInfo.CreationTimeUtc);

            BinaryFormatter serializer = new BinaryFormatter();


            SortedDictionary<string, long> dictionary = new SortedDictionary<string, long>(new Comparator());
            string[] directs = Directory.GetDirectories(args[0]);
            foreach (string file in directs) { 
                dictionary.Add(file, GetSize(file));
            }
            string[] files  = Directory.GetFiles(args[0]);
            foreach (string file in files)
            {
                dictionary.Add(file, GetSize(file));
            }
            /*foreach (KeyValuePair<string, long> element in dictionary)
            {
                Console.WriteLine(element);
            }*/
            using (var file = File.Open(@"c:temp\file.dat", FileMode.Create))
            {
                serializer.Serialize(file, dictionary);
                file.Position = 0;
                var newDict = (SortedDictionary<string, long>) serializer.Deserialize(file);
                foreach (KeyValuePair<string, long> element in newDict)
                {
                    Console.WriteLine(element);
                }
            }






        }
    }
}