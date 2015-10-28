//-----------------------------------------------------------------------------
//                                                                           --
//                                                                           --
//                   XXXXXXXX X      X XXXXXXXXX X       X                   --
//                   X        X      X     X     X       X                   --
//                   X        X      X     X     X       X                   --
//                   X        X      X     X     X       X                   --
//                   XXXXXXX  XXXXXXXX     X     X       X                   --
//                   X        X      X     X     X   X   X                   --
//                   X        X      X     X     X  X X  X                   --
//                   X        X      X     X     X X   X X                   --
//                   X        X      X     X     X       X                   --
//                                                                           --
//                                                                           --
//       F A C H H O C H S C H U L E   -   T E C H N I K U M   W I E N       --
//                                                                           --
//                       Embedded Systems Department                         --
//                                                                           --
//-----------------------------------------------------------------------------
//                                                                           --
//         Web:           http://embsys.technikum-wien.at/                   --
//                                                                           --
//         Contact:       hoeller@technikum-wien.at                          --
//                                                                           --
//-----------------------------------------------------------------------------
//
//         CVSROOT:                esshare:/cvsroot/kt_fsez
//
//         Author:                 Roland Höller
//
//         Filename:               convHex.c
//
//         Date of Creation:       Thu Aug 23 12:00:00 2001
//
//         Version:                $Revision: 1.2 $
//
//         Date of Latest Version: $Date: 2010/08/13 13:47:00 $
//
//
//         Description: Converts Intel HEX files to MIF, COE and MEM text 
//         files.
//                      
//
//
//
//
//-----------------------------------------------------------------------------
//
//  CVS Change Log:
//
//  $Log: convHex.c,v $
//  Revision 1.2  2010/08/13 13:47:00  kutschera
//  added generation of Quartus MIF output files (for 32k ROM Size)
//
//  Revision 1.1  2010/08/12 12:23:30  groeblinger
//  Initial checkin.
//
//  Revision 1.3  2008/11/12 15:49:32  kutschera
//  Modified Header
//
//  02.04.2008, Christoph Veigl, Christof Kutschera                 
//           found bug: wrong address placement if two address areas differ  
//                       by one one byte, corrected.                         
//                      DUA extension changed to MIF                         
//           19.11.2007, Christoph Veigl                                     
//                Added support for MEM output                               
//           removed memory allocation bug:                                  
//             fn=(char*)malloc(sizeof(argv[2])+1) changed to                
//             fn=(char*)malloc(strlen(argv[1])+1)                           
//           15.11.2007, Christof Kutschera                                  
//			 Added support for COE output                                    
//			 18.04.2006, Roland Höller;                                      
//           Added support for non-contigous addresses. Note: Segmented      
//           address record (Record Type = 0x02) and Extended Linear Address 
//           Record (Record Type = 0x04) not jet supported.                  
//-----------------------------------------------------------------------------

#include <sys/stat.h>    
#include <stdlib.h>    
#include <limits.h>    
#include <time.h>    
#include <sys/types.h> 
#include <stdio.h> 
#include <string.h> 
#include <unistd.h> 
#include <errno.h> 
 
#define LINE_LENGTH 128
#define FILE_EXT ".mif"
#define FILE_EXT_2 ".coe"
#define FILE_EXT_3 ".mem"
#define FILE_EXT_4 ".mif"
#define ROM_SIZE 32768

static char *cmnd;

FILE *fpr; /* Read  file pointer */
FILE *fpw; /* Write file pointer to MIF file*/
FILE *fpw2;/* Write file pointer to COE file)*/
FILE *fpw3;/* Write file pointer to MEM file)*/
FILE *fpw4;/* Write file pointer to Quartus MIF file)*/

/*---------------------------------------------------------------------------*/
/*  Function : error_exit                                                    */
/*                                                                           */
/*  Purpose :  Print error message, close files, and exit                    */
/*             program with exit code EXIT_FAILURE.                          */
/*  Input :    Message text                                                  */
/*---------------------------------------------------------------------------*/
void error_exit (char *msg) 
{ 
  (void) fprintf (stderr, "%s: %s: %s\n", cmnd, msg, strerror(errno)); 
/*   (void) fclose(fpr); */
/*   (void) fclose(fpw); */
  (void) fflush(stdout);
  exit (EXIT_FAILURE); 
}

/*---------------------------------------------------------------------------*/
/*  Function : hex2bin                                                       */
/*                                                                           */
/*  Purpose :  Convert a hexadecimal character in its binary representation  */
/*  Input :    Hexadecimal character                                         */
/*---------------------------------------------------------------------------*/
char *hex2bin (char hexval)
{
  /* Default is new line */
  static char bnibble[5] = "----\0";
  /* Convert hexadecimal character */
  if (hexval == '0') (void)strcpy(bnibble,"0000");
  if (hexval == '1') (void)strcpy(bnibble,"0001");
  if (hexval == '2') (void)strcpy(bnibble,"0010");
  if (hexval == '3') (void)strcpy(bnibble,"0011");
  if (hexval == '4') (void)strcpy(bnibble,"0100");
  if (hexval == '5') (void)strcpy(bnibble,"0101");
  if (hexval == '6') (void)strcpy(bnibble,"0110");
  if (hexval == '7') (void)strcpy(bnibble,"0111");
  if (hexval == '8') (void)strcpy(bnibble,"1000");
  if (hexval == '9') (void)strcpy(bnibble,"1001");
  if ((hexval == 'A') || (hexval == 'a')) (void)strcpy(bnibble,"1010");
  if ((hexval == 'B') || (hexval == 'b')) (void)strcpy(bnibble,"1011");
  if ((hexval == 'C') || (hexval == 'c')) (void)strcpy(bnibble,"1100");
  if ((hexval == 'D') || (hexval == 'd')) (void)strcpy(bnibble,"1101");
  if ((hexval == 'E') || (hexval == 'e')) (void)strcpy(bnibble,"1110");
  if ((hexval == 'F') || (hexval == 'f')) (void)strcpy(bnibble,"1111");
  return (bnibble);
} 
 
/*---------------------------------------------------------------------------*/
/*  Function : hex2int                                                       */
/*                                                                           */
/*  Purpose :  Convert a hexadecimal character in its integer representation */
/*  Input :    Hexadecimal character                                         */
/*---------------------------------------------------------------------------*/
int hex2int (char hexval)
{
  /* Default */
  int inibble = 17;
  /* Convert hexadecimal character */
  if (hexval == '0') inibble = 0;
  if (hexval == '1') inibble = 1;
  if (hexval == '2') inibble = 2;
  if (hexval == '3') inibble = 3;
  if (hexval == '4') inibble = 4;
  if (hexval == '5') inibble = 5;
  if (hexval == '6') inibble = 6;
  if (hexval == '7') inibble = 7;
  if (hexval == '8') inibble = 8;
  if (hexval == '9') inibble = 9;
  if ((hexval == 'A') || (hexval == 'a')) inibble = 10;
  if ((hexval == 'B') || (hexval == 'b')) inibble = 11;
  if ((hexval == 'C') || (hexval == 'c')) inibble = 12;
  if ((hexval == 'D') || (hexval == 'd')) inibble = 13;
  if ((hexval == 'E') || (hexval == 'e')) inibble = 14;
  if ((hexval == 'F') || (hexval == 'f')) inibble = 15;
  return (inibble);
}  

/*****************************************************************************/
/*                                                                           */
/*                               MAIN PROGRAM                                */
/*                                                                           */
/*****************************************************************************/
int main (int argc, char **argv) 
{ 
  char line[LINE_LENGTH];
  char nline[LINE_LENGTH*4];
  char *fwname;
  char *fwname2;
  char *fwname3;
  char *fwname4;
  char temp_string[10];
  int nmbr = 0;
  int n = 0;
  int i = 0;
  int address = 0;
  int lastaddress = 0;
  int length = 0;
  int address0 = 0;
  int address1 = 0;
  int sorted = 0;
  unsigned int linecount=0;
  unsigned int type_error_cnt = 0;

  /* Declare linked list to temporarily store the Intel Hex records for      */
  /* sorting them by ascending addresses.                                    */
  struct ihexlines {
    char ihexline[LINE_LENGTH];
    struct ihexlines *nextline;
  };

  typedef struct ihexlines IHEXRECORD;
  typedef IHEXRECORD *nextrecord;

  nextrecord pHead = NULL;
  nextrecord pNew  = NULL;
  nextrecord pAct  = NULL;
  nextrecord pPre0  = NULL;
  nextrecord pPre1  = NULL;
 
  nline[0] = '\0';
  cmnd = argv[0];

  /* Check options and count them */
  if ((argc != 2) || (!strstr(argv[1],".hex")))
    error_exit ("Usage: convHex <filename.hex>");

  /* Build file name for new MIF file */ 
  fwname = (char*)malloc(strlen(argv[1]) + 1);
  if (fwname == NULL)
    error_exit ("Not enough memory");
  fwname[0] = 0;
  (void)strcpy(fwname,argv[1]);
  (void)strcpy(fwname + strlen(fwname) - 4,FILE_EXT);
  fprintf(stderr,"fwname = %s\n",fwname);

  /* Build file name for new COE file */
  fwname2 = (char*)malloc(strlen(argv[1]) + 1);
  if (fwname2 == NULL)
    error_exit ("Not enough memory");
  fwname2[0] = 0;
  (void)strcpy(fwname2,argv[1]);
  (void)strcpy(fwname2 + strlen(fwname2) - 4,FILE_EXT_2);
  fprintf(stderr,"fwname2 = %s\n",fwname2);


  /* Build file name for new MEM file */
  fwname3 = (char*)malloc(strlen(argv[1]) + 1);
  if (fwname3 == NULL)
    error_exit ("Not enough memory");
  fwname3[0] = 0;
  (void)strcpy(fwname3,argv[1]);
  (void)strcpy(fwname3 + strlen(fwname3) - 4,FILE_EXT_3);
  fprintf(stderr,"fwname3 = %s\n",fwname3);
  
  /* Build file name for new Quartus MIF file */
  fwname4 = (char*)malloc(strlen(argv[1]) + strlen("_quartus") + 1);
  if (fwname4 == NULL)
    error_exit ("Not enough memory");
  fwname4[0] = 0;
  (void)strcpy(fwname4,argv[1]);
  (void)strcpy(fwname4 + strlen(fwname4) - 4,"_quartus");
  (void)strcpy(fwname4 + strlen(fwname4),FILE_EXT_4);
  fprintf(stderr,"fwname4 = %s\n",fwname4);


  /* Open file */
  if ((fpr = fopen (argv[1],"r")) == NULL)
    error_exit ("Cannot open input file!");

  /* Create MIF file */
  if ((fpw = fopen (fwname,"w")) == NULL)
    error_exit ("Cannot create output file!");

  /* Create COE file and insert header */
  if ((fpw2 = fopen (fwname2,"w")) == NULL)
    error_exit ("Cannot create output file!");
  fputs("memory_initialization_radix = 2;\n", fpw2);
  fputs("memory_initialization_vector =\n", fpw2);

  /* Create MEM file and insert header */
  if ((fpw3 = fopen (fwname3,"w")) == NULL)
    error_exit ("Cannot create output file!");
    
  /* Create Quartus MIF file and insert header */
  if ((fpw4 = fopen (fwname4,"w")) == NULL)
    error_exit ("Cannot create output file!");
  fputs("WIDTH=8;\n", fpw4);
  sprintf(nline,"DEPTH=%d;\n\n", ROM_SIZE);
  fputs(nline,fpw4);
  nline[0]='\0';
  fputs("ADDRESS_RADIX=HEX;\n", fpw4);
  fputs("DATA_RADIX=HEX;\n\n", fpw4);
  fputs("CONTENT BEGIN\n", fpw4);
  
  /* Read in the Intel Hex file to sort the lines by their addresses */
  while (fgets (line, sizeof(line), fpr) != NULL && 
         strncmp(line,":00000001FF",11) != 0)
    {
      if (n == 0)
        {
          pNew = (nextrecord)malloc(sizeof(IHEXRECORD));
          pNew->nextline = NULL;
          pHead = pNew;
          pAct = pNew;
          (void)strcpy(pNew->ihexline,line);
	}
      else
        {
          pNew = (nextrecord)malloc(sizeof(IHEXRECORD));
          pAct->nextline = pNew;
          pNew->nextline = NULL;
          (void)strcpy(pNew->ihexline,line);
          pAct = pAct->nextline;
	}
      n++;
    }
  n = 0;
  /* Print out original Intel Hex file to check validity */
  pAct = pHead;
  while (pAct != NULL)
    {
      printf("%s",pAct->ihexline);
      pAct = pAct->nextline;
    }
  printf("\n\n");
  /* Now sort the original Intel Hex file lines by their addresses */
  sorted = 0;
  while (sorted == 0)
    {
  sorted = 1;
  pAct = pHead;
  pPre0 = pHead;
  pPre1 = pHead;
  i = 0;
  if (pAct->nextline != NULL && pAct != NULL)
    {
      n = 3;
      i++;
      temp_string[0] = pAct->ihexline[n++];
      temp_string[1] = pAct->ihexline[n++];
      temp_string[2] = pAct->ihexline[n++];
      temp_string[3] = pAct->ihexline[n++];
      temp_string[4] = '\0';
      sscanf(temp_string,"%X",&address0);
      /*printf("%d\n",address0);*/
      pAct = pAct->nextline;
    }
  while (pAct != NULL)
    {
      n = 3;
      i++;
      temp_string[0] = pAct->ihexline[n++]; 
      temp_string[1] = pAct->ihexline[n++];
      temp_string[2] = pAct->ihexline[n++];
      temp_string[3] = pAct->ihexline[n++];
      temp_string[4] = '\0';
      sscanf(temp_string,"%X",&address1);
      /*printf("%d\n",address1);*/
      if (address0 > address1)
        {
        /* The two Intel Hex records have to be exchanged */
        sorted = 0;
        if (i == 2)
          {
            pPre0->nextline = pAct->nextline;
            pHead = pAct;
            pAct->nextline = pPre0;
          }
        else
          {
            pPre0->nextline = pAct->nextline;
            pPre1->nextline = pAct;
            pAct->nextline = pPre0;
          }
        }

      address0 = address1;
      pPre1 = pPre0;
      pPre0 = pAct;
      pAct = pAct->nextline;
    }
    }
  /* Print out sorted Intel Hex file to check validity */
  pAct = pHead;
  while (pAct != NULL)
    {
      printf("%s",pAct->ihexline);
      pAct = pAct->nextline;
    }

  /* Write converted file */
  nline[0] = '\0';
  pAct = pHead;
  lastaddress = 0;
  while (pAct != NULL)
    {
      n = 1; /* Extract length of record */
      temp_string[0] = pAct->ihexline[n++];
      temp_string[1] = pAct->ihexline[n++];
      temp_string[2] = '\0';
      sscanf(temp_string,"%X",&length);
      printf("length %s %d\n",temp_string,length);
      n = 3; /* Extract address of record */
      temp_string[0] = pAct->ihexline[n++];
      temp_string[1] = pAct->ihexline[n++];
      temp_string[2] = pAct->ihexline[n++];
      temp_string[3] = pAct->ihexline[n++];
      temp_string[4] = '\0';
      sscanf(temp_string,"%X",&address);
      printf("address 0x%x\n",address);
      if (pAct->ihexline[8] != '0') 
      {
        pAct = pAct->nextline; 
        type_error_cnt++;
      }
      else
      {
      if (address > lastaddress)
        {
          /* Insert all zero entries up to the next address */
          for ( n = lastaddress; n < address; n++)
            {
              (void)strcpy(nline,"00000000\n");         // for MIF file
              fputs (nline,fpw);
			  (void)strcpy(nline,"00000000,\n");        // for coe file
			  fputs (nline,fpw2);

			  sprintf(nline,"@%04X 00\n",linecount);  // for MEM file
			  fputs (nline,fpw3);

        sprintf(nline,"%x:0;\n",linecount);  // for Quartus MIF file
			  fputs (nline,fpw4);

			  nline[0] = '\0';
        linecount++;
            }
        }
      n = 1;
      nmbr = hex2int(pAct->ihexline[n]);
      n++;
      nmbr = nmbr*16 + hex2int(pAct->ihexline[n]);
      n++;
      n++;
      n++;
      for ( n = 5; n < nmbr+5; n++)
      {
        (void)strcpy(nline,hex2bin(pAct->ihexline[2*n-1]));   // prepare MIF file data
        (void)strcpy(nline + 4,hex2bin(pAct->ihexline[2*n]));
        (void)strcpy(nline + 8 ,"\n");
        fputs (nline,fpw);                                   // write MIF file data

		if ((pAct->nextline == NULL) && ((n+1) >= (nmbr+5))) /* coe file, detect last line */
		{
			(void)strcpy(nline + 8 ,";\n");
		}
		else
		{
		    (void)strcpy(nline + 8 ,",\n");
		}
        fputs (nline,fpw2);                                 // write coe file data

	      sprintf(nline,"@%04X ",linecount);  // for MEM file
  	    fputs (nline,fpw3);

        nline[0]=pAct->ihexline[2*n-1];          // prepare mem file data
        nline[1]=pAct->ihexline[2*n];
		    nline[2]='\n';
		    nline[3]='\0';
        fputs (nline,fpw3);                     // write mem file data
        
        sprintf(nline,"%x:",linecount);             // for Quartus MIF file
		    fputs (nline,fpw4);
        nline[0]=pAct->ihexline[2*n-1];        // prepare Quartus MIF file data
        nline[1]=pAct->ihexline[2*n];
		    nline[2]=';';
        nline[3]='\n';
		    nline[4]='\0';
        fputs (nline,fpw4);                     // write Quartus MIF file data


    		nline[0] = '\0';
        linecount++;
      }
      pAct = pAct->nextline;
      lastaddress = address + length;
    }
    }
    
  // fill up Quartus MIF file with zeros:
  if (linecount < (ROM_SIZE-1))
  {
    while (linecount<(ROM_SIZE))
    {
      sprintf(nline,"%x:0;\n",linecount);  
      fputs(nline, fpw4); 
      linecount++;
    }
  }

  (void) fclose(fpr);
  (void) fclose(fpw);
  (void) fclose(fpw2);
  (void) fclose(fpw3);
  sprintf(nline,"END;\n");
  fputs(nline,fpw4);
  (void) fclose(fpw4);
  printf("Conversion finished with %d type errors.", type_error_cnt);

  if (fflush(stdout) == EOF)
    error_exit ("Standard output flush error!");

  return (0);
} 
     
