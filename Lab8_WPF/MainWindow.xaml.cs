using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using System.IO;

namespace Lab8_WPF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void addFilesToTree(string path, TreeViewItem root)
        {
            var files = Directory.GetFiles(path);
            foreach (var item in files)
            {
                var itemTree = new TreeViewItem
                {
                    Header = System.IO.Path.GetFileName(item),
                    Tag = item,
                    ContextMenu = new ContextMenu()
                };
                var menuItemOpen = new MenuItem
                {
                    Header = "Open",
                    Tag = item
                };
                menuItemOpen.Click += openClickTreeItem;

                var menuItemDelete = new MenuItem
                {
                    Header = "Delete",
                    Tag = item
                };
                menuItemDelete.Click += deleteClickTreeItem;

                itemTree.ContextMenu.Items.Add(menuItemOpen);
                itemTree.ContextMenu.Items.Add(menuItemDelete);


                root.Items.Add(itemTree);
            }
        
            var directories = Directory.GetDirectories(path);
            foreach (var directory in directories)
            {
                var itemTree = new TreeViewItem
                {
                    Header = System.IO.Path.GetFileName(directory),
                    Tag = directory,
                    ContextMenu = new ContextMenu()
                };
                var menuItemOpen = new MenuItem
                {
                    Header = "Create",
                    Tag = itemTree
                };
                menuItemOpen.Click += createClickTreeItem;

                var menuItemDelete = new MenuItem
                {
                    Header = "Delete",
                    Tag = itemTree
                };
                menuItemDelete.Click += deleteClickTreeItem;

                itemTree.ContextMenu.Items.Add(menuItemOpen);
                itemTree.ContextMenu.Items.Add(menuItemDelete);
                root.Items.Add(itemTree);
                addFilesToTree((string)directory, itemTree);
            }

        }

        private void openClickTreeItem(object sender, RoutedEventArgs e)
        {
            var menuItem = e.Source as MenuItem;

            if (menuItem?.Tag is not string path || !File.Exists(path))
            {
                return;
            }

            FileViewer.Text = File.ReadAllText(path, Encoding.UTF8);
        }

        private void deleteClickTreeItem(object sender, RoutedEventArgs e)
        {
            MenuItem root = (MenuItem)sender;
            var path = root.Tag as string;
            FileAttributes attr = File.GetAttributes(path);
            if (attr.HasFlag(FileAttributes.Directory)) { 
                Directory.Delete(path);
            }
            else
            {
                File.Delete(path);
            }
            TreeViewItem item = (TreeViewItem)(treeView.SelectedItem);

        }

        private void createClickTreeItem(object sender, RoutedEventArgs e)
        {

        }

        private void openClick(object sender, RoutedEventArgs e)
        {
            var dlg = new FolderBrowserDialog() { Description = "Select directory to open" };
            if(dlg.ShowDialog()== System.Windows.Forms.DialogResult.OK)
            {
                var path = dlg.SelectedPath;
                DirectoryInfo directoryInfo = new DirectoryInfo(path);

                var root = new TreeViewItem
                {
                    Header = System.IO.Path.GetFileName(path),
                    Tag = path
                };
                treeView.Items.Add(root);
                addFilesToTree(path, root);
            }
      
        }

        private void exitClick(object sender, RoutedEventArgs e)
        {
            System.Windows.Application.Current.Shutdown();
        }
    }
}
