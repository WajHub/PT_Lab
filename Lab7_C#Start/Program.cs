using System;
using System.IO;
using System.IO.Compression;

namespace Lab7
{
    static class Extension
    {
        public static FileInfo? GetOldestFile(this DirectoryInfo directory)
        {
            DateTime oldestDate = DateTime.Now;
            FileInfo? f = null;
            foreach (var file in directory.GetFiles())
            {
                if (file.LastWriteTime < oldestDate)
                {
                    oldestDate = file.LastWriteTime;
                    f = file;
                }
            }
            foreach (var subDirectory in directory.GetDirectories())
            {
                var oldestSubDirFile = GetOldestFile(subDirectory);
                if (oldestSubDirFile!=null && oldestSubDirFile.CreationTimeUtc < oldestDate)
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
    
    internal class Program
    {
        private static void print_files(string path)
        { 
            string[] files = Directory.GetFiles(path);
            foreach (string file in files) {
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
            Console.WriteLine("        Amount:"+amount);
            
            // print files
            string[] files = Directory.GetFiles(path);
            foreach (string file in files) {
                Console.Write('f');
                for (int i = 0; i < depth; i++)
                {
                    Console.Write(" -- ");
                }
                Console.Write(file);
                Console.WriteLine("        Atr: "+Extension.GetAtrDos(new DirectoryInfo(path)));
            }

            string[] directories = Directory.GetDirectories(path);

            foreach (var dir in directories)
            {   
                print_recursive(dir, ++depth);
            }
            


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

            // FileInfo? fileInfo = Extension.GetOldestFile(new DirectoryInfo(args[0]));
            // Console.WriteLine(fileInfo.Name);
            




        }
    }
}