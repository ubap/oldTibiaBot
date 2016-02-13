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
        ThroniaBot throniaBot;
        public Form1(ThroniaBot _throniaBot)
        {
            throniaBot = _throniaBot;
            InitializeComponent();     
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            throniaBot.FullLight = checkBox1.Checked;
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            //BattleListEntry self = throniaMemory.getSelf();
            //label1.Text = self.getName();
            //label2.Text = "x: " + self.getPos_x().ToString()
            //    + ", y: " + self.getPos_y().ToString()
            //    + ", z: " + self.getPos_z().ToString();


            //ThroniaTest throniaTest = new ThroniaTest(throniaMemory);
            //throniaTest.RunTests();

 
        }

        private void button1_Click(object sender, EventArgs e)
        {

            //throniaSender.Say("hahah");
            //throniaSender.UseWith(65535, 10, 0, 2580, 0, 32377, 32177, 7, 490, 0);
            //throniaMemory.MapClick(32354, 32153, 11);
            //throniaMemory.getEquipment();
            //int containerIndex = 0;
           //int slotIndex = 0;
            //throniaController.FindItemInContainers(2580, ref containerIndex, ref slotIndex);
            //throniaController.FindItemInInventory(2580, ref slotIndex);
            //throniaController.EatFood();
            //throniaController.BurnMana();
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {
            //if (checkBox2.Checked == true)
            //{
            //    throniaController.startAutoFish();
            //}
            //else
            //{
            //    throniaController.stopAutoFish();
            //}
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            //throniaController.stopAll();
        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {
            //if (checkBox3.Checked == true)
            //{
            //    throniaController.startAutoEatFood();
            //}
            //else
            //{
            //    throniaController.stopAutoEatFood();
            //}
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
