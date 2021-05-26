const width = 5;
const height = 4;

let color_sample = ["red","red","orange","orange","yellow","yellow",
        "green","green","blue","blue","purple","purple",
        "aquamarine","aquamarine","gold","gold","violet","violet",
        "tan","tan"];
let flag = true;
function shuffle(list){
    let color_list = [];
    for (let i = 0; 0 < list.length; i++) {
        color_list = color_list.concat(list.splice(Math.floor(Math.random()*list.length),1));


    }
    return color_list
}
let color_list = shuffle(color_sample);

function cardSetting(width, height) {
    flag = false;
    for (let i = 0; i < width * height; i++) {
        const div_flip_card = document.createElement("div");
        div_flip_card.className = "flip-card";
        const div_flip_card_inner = document.createElement("div");
        div_flip_card_inner.className = "flip-card-inner";
        const div_flip_card_front = document.createElement("div");
        div_flip_card_front.className = "flip-card-front";
        const div_flip_card_back = document.createElement("div");
        div_flip_card_back.className = "flip-card-back";
        div_flip_card_back.style.background = color_list[i];
        div_flip_card_inner.appendChild(div_flip_card_front);
        div_flip_card_inner.appendChild(div_flip_card_back);
        div_flip_card.appendChild(div_flip_card_inner);
        div_flip_card.addEventListener("click", ev => {
            if(flag){
                div_flip_card.classList.toggle("flipped");
                console.log(i);
            }
        })
        document.body.appendChild(div_flip_card);

    }
    document.querySelectorAll(".flip-card").forEach((value, key) => {
        setTimeout(()=>{
            value.classList.toggle("flipped");
        },1000+100*key)
    });
    setTimeout(()=>{
        document.querySelectorAll(".flip-card").forEach((value, key) => {
            value.classList.toggle("flipped");
        });
        flag = true;

    },4000)

}

cardSetting(width, height);
