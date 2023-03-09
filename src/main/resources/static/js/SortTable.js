
function sortTable(n) {

    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("tableUsers");
    switching = true;

    //Set the sorting direction to ascending:
    dir = "asc";


    // TEST

    rowHeader = table.getElementsByTagName("TR")[0];
    columnHeader = rowHeader.getElementsByTagName("TH")[n];
    columnDiv = columnHeader.getElementsByTagName("DIV")[0];
    spanDiv = columnDiv.getElementsByTagName("SPAN")[0];
//    svgTag = spanDiv.getElementsByTagName("SVG")[0];
    svgTagQuerySelector = spanDiv.querySelector("SVG");
    svgGetElement = spanDiv.getElementsByTagName("svg")[0];

//    console.log( "rowHeader: " + rowHeader.innerHTML );
//    console.log( "rowHeader: " + rowHeader.getElementsByTagName("TH")[n].innerHTML );
//
//    console.log( "columnHeaderDIV: " + columnDiv.innerHTML );
//
//    console.log("spanDiv: " + spanDiv.innerHTML );
//
//    console.log("svgTagQuerySelector: " + svgTagQuerySelector.className );
//    console.log("svgGetElement: " + svgGetElement.innerHTML );
//
//    console.log("svgGetElement.className: " + svgGetElement.classList );

    ele = svgGetElement;
    oldClass = "fa-arrow-up";
    newClass = "fa-arrow-down";

    replaceClass(ele, oldClass, newClass);

    /*Make a loop that will continue until
    no switching has been done:*/
    while (switching) {

        //start by saying: no switching is done:
        switching = false;
        rows = table.getElementsByTagName("TR");

        /*Loop through all table rows (except the
        first, which contains table headers):*/
        for (i = 1; i < rows.length - 1; i++) { //Change i=0 if you have the header th a separate table.

            //start by saying there should be no switching:
            shouldSwitch = false;

            /*Get the two elements you want to compare,
            one from current row and one from the next:*/
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];

            /*check if the two rows should switch place,
            based on the direction, asc or desc:*/
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {

                    //if so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {

                    //if so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }

        if (shouldSwitch) {

            /*If a switch has been marked, make the switch
            and mark that a switch has been done:*/
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);

            switching = true;

            //Each time a switch is done, increase this count by 1:
            switchcount++;

        } else {

            /*If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again.*/
            if (switchcount == 0 && dir == "asc") {
            dir = "desc";
            switching = true;
            }
        }
    }
}
function hasClass(ele, cls) {
    console.log("hasClass() - ele: " + ele);

    return ele.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));

//    return ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

function addClass(ele, cls) {
    console.log("addClass START");
    console.log("addClass: ele - " + ele);
    console.log("addClass: cls - " + cls);
//    if (!this.hasClass(ele, cls)) ele.className += " " + cls;
    if (!this.hasClass(ele, cls)) ele += " " + cls;

    console.log("addClass: ele - " + ele );
}

function removeClass(ele, cls) {
    if (hasClass(ele, cls)) {

        console.log("removeClass: START" );
        console.log("removeClass: ele before - " + ele );

        console.log("removeClass: cls - " + cls );

        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');

        ele = ele.replace(reg, ' ');
//        ele.className = ele.className.replace(reg, ' ');

        console.log("removeClass: ele after- " + ele );
    }
}

function replaceClass(ele, oldClass, newClass){

    for(i = 0 ; ele.classList.length ; i++){

        console.log("ele.classList[" + i + "]: " + ele.classList[i]);

            if(hasClass(ele.classList[i], oldClass)){
                console.log("hasClass TRUE");
                removeClass(ele.classList[i], oldClass);
                addClass(ele.classList[i], newClass);

                console.log("ele after: " + ele.classList[i]);

                break;
            }
    }
    return;

//    if(hasClass(ele, oldClass)){
//        console.log("hasClass TRUE");
//        removeClass(ele, oldClass);
//        addClass(ele, newClass);
//    }
//    return;
}
