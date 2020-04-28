<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->

<!-- Begin
function setDataType(cValue)
  {
    // THIS FUNCTION CONVERTS DATES AND NUMBERS FOR PROPER ARRAY
    // SORTING WHEN IN THE SORT FUNCTION
    var isDate = new Date(cValue);
    if (isDate == "NaN")
      {
        if (isNaN(cValue))
          {
            // THE VALUE IS A STRING, MAKE ALL CHARACTERS IN
            // STRING UPPER CASE TO ASSURE PROPER A-Z SORT
            cValue = cValue.toUpperCase();
            return cValue;
          }
        else
          {
            // VALUE IS A NUMBER, TO PREVENT STRING SORTING OF A NUMBER
            // ADD AN ADDITIONAL DIGIT THAT IS THE + TO THE LENGTH OF
            // THE NUMBER WHEN IT IS A STRING
            var myNum;
            myNum = String.fromCharCode(48 + cValue.length) + cValue;
            return myNum;
          }
        }
  else
      {
        // VALUE TO SORT IS A DATE, REMOVE ALL OF THE PUNCTUATION AND
        // AND RETURN THE STRING NUMBER
        //BUG - STRING AND NOT NUMERICAL SORT .....
        // ( 1 - 10 - 11 - 2 - 3 - 4 - 41 - 5  etc.)
        var myDate = new String();
        myDate = isDate.getFullYear() + " " ;
        myDate = myDate + isDate.getMonth() + " ";
        myDate = myDate + isDate.getDate(); + " ";
        myDate = myDate + isDate.getHours(); + " ";
        myDate = myDate + isDate.getMinutes(); + " ";
        myDate = myDate + isDate.getSeconds();
        //myDate = String.fromCharCode(48 + myDate.length) + myDate;
        return myDate ;
      }
  }
function sortTable(col, tableToSort)
  {
    var iCurCell = col + tableToSort.cols;
    var totalRows = tableToSort.rows.length;
    var bSort = 0;
    var colArray = new Array();
    var oldIndex = new Array();
    var indexArray = new Array();
    var bArray = new Array();
    var newRow;
    var newCell;
    var i;
    var c;
    var j;
    // ** POPULATE THE ARRAY colArray WITH CONTENTS OF THE COLUMN SELECTED
    for (i=1; i < tableToSort.rows.length; i++)
      {
        colArray[i - 1] = setDataType(tableToSort.cells(iCurCell).innerText);
        iCurCell = iCurCell + tableToSort.cols;
      }
    // ** COPY ARRAY FOR COMPARISON AFTER SORT
    for (i=0; i < colArray.length; i++)
      {
        bArray[i] = colArray[i];
      }
    // ** SORT THE COLUMN ITEMS
    //alert ( colArray );
    colArray.sort();
    //alert ( colArray );
    for (i=0; i < colArray.length; i++)
      { // LOOP THROUGH THE NEW SORTED ARRAY
        indexArray[i] = (i+1);
        for(j=0; j < bArray.length; j++)
          { // LOOP THROUGH THE OLD ARRAY
            if (colArray[i] == bArray[j])
              {  // WHEN THE ITEM IN THE OLD AND NEW MATCH, PLACE THE
                // CURRENT ROW NUMBER IN THE PROPER POSITION IN THE
                // NEW ORDER ARRAY SO ROWS CAN BE MOVED ....
                // MAKE SURE CURRENT ROW NUMBER IS NOT ALREADY IN THE
                // NEW ORDER ARRAY
                for (c=0; c<i; c++)
                  {
                    if ( oldIndex[c] == (j+1) )
                    {
                      bSort = 1;
                    }
                      }
                      if (bSort == 0)
                        {
                          oldIndex[i] = (j+1);
                        }
                          bSort = 0;
                        }
          }
    }
  // ** SORTING COMPLETE, ADD NEW ROWS TO BASE OF TABLE ....
  for (i=0; i<oldIndex.length; i++)
    {
      newRow = tableToSort.insertRow();
      for (c=0; c<tableToSort.cols; c++)
        {
          newCell = newRow.insertCell();
          newCell.innerHTML = tableToSort.rows(oldIndex[i]).cells(c).innerHTML;
        }
      }
  //MOVE NEW ROWS TO TOP OF TABLE ....
  for (i=1; i<totalRows; i++)
    {
      tableToSort.moveRow((tableToSort.rows.length -1),1);
    }
  //DELETE THE OLD ROWS FROM THE BOTTOM OF THE TABLE ....
  for (i=1; i<totalRows; i++)
    {
      tableToSort.deleteRow();
    }
  }
//  End -->
