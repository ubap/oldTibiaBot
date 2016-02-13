using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Thronia
{
    public partial class ClientSelector : Form
    {
        ThroniaClientDescription[] descriptors;
        public ClientSelector()
        {
            InitializeComponent();
        }

        private void PopulateComboBox()
        {
            descriptors = ThroniaInjector.getClients();
            comboBox1.Items.Clear();
            foreach(ThroniaClientDescription d in descriptors)
            {
                comboBox1.Items.Add(d.getName());
            }
            if (comboBox1.Items.Count > 0)
            {
                comboBox1.SelectedIndex = 0;
            }
        }

        private void ClientSelector_Load(object sender, EventArgs e)
        {
            PopulateComboBox();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            PopulateComboBox();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int selected = comboBox1.SelectedIndex;
            int pId = descriptors[selected].getpId();

            this.Hide();
            try
            {
                ThroniaInjector.Inject(pId);
                ThroniaBot bot = new ThroniaBot(pId);
                Form mainWindow = new Form1(bot);
                MessageBox.Show("In like Flynn", "Success:)");
                mainWindow.Show();
            }
            catch(Exception exception)
            {
                MessageBox.Show("Could not inject.\n" + exception.Message, "Error.");
                // find out what is blocking app from exiting on its own?
                Application.Exit();
            }
            

        }

        private void ClientSelector_FormClosed(object sender, FormClosedEventArgs e)
        {}
    }
}
