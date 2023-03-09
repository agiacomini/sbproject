
//window.addEventListener("load",function() { changeBackground() });
function changeBackground(color) {

   document.body.style.background = '#' + Math.floor(Math.random()*16777215).toString(16);

}


window.addEventListener("load",function() { genHexString() });
function genHexString() {

    const avatars = document.getElementsByClassName('avatar');
    for(let i = 0; i < avatars.length; i++){

        const randomColor = Math.floor(Math.random()*16777215).toString(16);
        avatars[i].style.background = '#' + randomColor;

        if( !(avatars[i].style.background != '') ){
            avatars[i].style.background = '#' + '009578';
        }
    }
}