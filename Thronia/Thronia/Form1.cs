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
    public partial class Form1 : Form
    {
        ThroniaMemory throniaMemory;
        ThroniaController throniaController;
        ThroniaSender throniaSender;
        public Form1()
        {
            InitializeComponent();
            throniaMemory = new ThroniaMemory();
            throniaMemory.AttachToProcess();

            throniaSender = new ThroniaSender();

            throniaController = new ThroniaController(throniaMemory, throniaSender);

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            //throniaController.setFullLight();
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked == true)
            {
                throniaController.startFullLight();
            }
            else
            {
                throniaController.stopFullLight();
            }
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            BattleListEntry self = throniaMemory.getSelf();
            label1.Text = self.getName();
            label2.Text = "x: " + self.getPos_x().ToString()
                + ", y: " + self.getPos_y().ToString()
                + ", z: " + self.getPos_z().ToString();


            ThroniaTest throniaTest = new ThroniaTest(throniaMemory);
            throniaTest.RunTests();

 
        }

        private void button1_Click(object sender, EventArgs e)
        {

            //throniaSender.Say("hahah");
            //throniaSender.UseWith(65535, 10, 0, 2580, 0, 32377, 32177, 7, 490, 0);
            //throniaMemory.MapClick(32354, 32153, 11);
            //throniaMemory.getEquipment();
            int containerIndex = 0;
            int slotIndex = 0;
            //throniaController.FindItemInContainers(2580, ref containerIndex, ref slotIndex);
            throniaController.FindItemInInventory(2580, ref slotIndex);
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox2.Checked == true)
            {
                throniaController.startAutoFish();
            }
            else
            {
                throniaController.stopAutoFish();
            }
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            throniaController.stopAll();
        }
    }
}
