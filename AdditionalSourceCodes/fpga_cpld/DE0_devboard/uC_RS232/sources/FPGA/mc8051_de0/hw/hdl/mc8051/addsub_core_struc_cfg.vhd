-------------------------------------------------------------------------------
--                                                                           --
--          X       X   XXXXXX    XXXXXX    XXXXXX    XXXXXX      X          --
--          XX     XX  X      X  X      X  X      X  X           XX          --
--          X X   X X  X         X      X  X      X  X          X X          --
--          X  X X  X  X         X      X  X      X  X         X  X          --
--          X   X   X  X          XXXXXX   X      X   XXXXXX      X          --
--          X       X  X         X      X  X      X         X     X          --
--          X       X  X         X      X  X      X         X     X          --
--          X       X  X      X  X      X  X      X         X     X          --
--          X       X   XXXXXX    XXXXXX    XXXXXX    XXXXXX      X          --
--                                                                           --
--                                                                           --
--                       O R E G A N O   S Y S T E M S                       --
--                                                                           --
--                            Design & Consulting                            --
--                                                                           --
-------------------------------------------------------------------------------
--                                                                           --
--         Web:           http://www.oregano.at/                             --
--                                                                           --
--         Contact:       8051@oregano.at                                  --
--                                                                           --
-------------------------------------------------------------------------------
--                                                                           --
--  MC8051 - VHDL 8051 Microcontroller IP Core                               --
--  Copyright (C) 2001 OREGANO SYSTEMS                                       --
--                                                                           --
--  This library is free software; you can redistribute it and/or            --
--  modify it under the terms of the GNU Lesser General Public               --
--  License as published by the Free Software Foundation; either             --
--  version 2.1 of the License, or (at your option) any later version.       --
--                                                                           --
--  This library is distributed in the hope that it will be useful,          --
--  but WITHOUT ANY WARRANTY; without even the implied warranty of           --
--  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        --
--  Lesser General Public License for more details.                          --
--                                                                           --
--  Full details of the license can be found in the file LGPL.TXT.           --
--                                                                           --
--  You should have received a copy of the GNU Lesser General Public         --
--  License along with this library; if not, write to the Free Software      --
--  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  --
--                                                                           --
-------------------------------------------------------------------------------
--
--
--         Author:                 Roland H�ller
--
--         Filename:               addsub_core_struc_cfg.vhd
--
--         Date of Creation:       Mon Aug  9 12:14:48 1999
--
--         Version:                $Revision: 1.1 $
--
--         Date of Latest Version: $Date: 2010/08/12 12:23:07 $
--
--
--         Description: Adder/Subtractor with carry/borrow, arbitrary data 
--                      width, overflow, and nibble carry for decimal 
--                      adjustment.
--
--
--
--
-------------------------------------------------------------------------------
configuration addsub_core_struc_cfg of addsub_core is
  for struc 
    for gen_smorequ_four
      for addsub_ovcy_1 : addsub_ovcy
        use configuration work.addsub_ovcy_rtl_cfg;
      end for;
    end for;
    for gen_greater_four
      for gen_addsub
        for gen_nibble_addsub
  	  for all : addsub_cy
            use configuration work.addsub_cy_rtl_cfg;
          end for;
        end for;
        for gen_last_addsub
  	  for all : addsub_ovcy
            use configuration work.addsub_ovcy_rtl_cfg;
          end for;
        end for;
      end for;
    end for;
  end for;
end addsub_core_struc_cfg;
