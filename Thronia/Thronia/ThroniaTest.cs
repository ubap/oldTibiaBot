using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Thronia
{
    class ThroniaTest
    {
        ThroniaMemory throniaMemory;
        public ThroniaTest(ThroniaMemory _throniaMemory)
        {
            throniaMemory = _throniaMemory;
        }

        public void RunTests()
        {
            TestTileOffset();
                
        }

        void TestTileOffset()
        {
            Map map = throniaMemory.getMap();
            for(int x=-7; x<=7; x++)
            {
                for(int y =-6; y<=6; y++)
                {
                    Tile testTile = map.getTile(x, y);
                    if (testTile.getOffsetX() != x || testTile.getOffsetY() != y)
                        throw new Exception("Tile offset calculation failed. (x=" 
                            + x.ToString() + ", y=" +y.ToString() + ").");
                }
            }

        }

    }
}
