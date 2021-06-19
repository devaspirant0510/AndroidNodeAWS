const width = 5;
const height = 4;

let startTime;
let endTime;

let color_sample = ["red","red","orange","orange","yellow","yellow",
        "green","green","blue","blue","purple","purple",
        "aquamarine","aquamarine","gold","gold","violet","violet",
        "tan","tan"];
let flag = true;
let card2count = [];
let openCardList = [];
//
// const retry_btn = document.querySelector("#retry-btn");
// const retry_btn = document.querySelector("#ranking-btn");

// retry_btn.addEventListener("click",(ev)=>{
//     location.reload();
// })
function shuffle(list){
    let color_list = [];
    for (let i = 0; 0 < list.length; i++) {
        color_list = color_list.concat(list.splice(Math.floor(Math.random()*list.length),1));
    }
    return color_list
}
let color_list = shuffle(color_sample);

function game(div_flip_card){
    // 카드 클릭했을때 이벤트 처리부분
    div_flip_card.addEventListener("click", ev => {
        if(flag){
            //기본적으로 뒤집히면 카드 뒤집어 줌
            console.log(openCardList);
            console.log(card2count);
            if (openCardList.includes(div_flip_card)){
                console.log("include");
            }else{
                div_flip_card.classList.add("flipped");
                card2count.push(div_flip_card);
                openCardList.push(div_flip_card);
                // 2개를 뒤집었을때
                if(card2count.length===2){
                    const firstColor = card2count[0].querySelector(".flip-card-back").style.background;
                    const endColor = card2count[1].querySelector(".flip-card-back").style.background;
                    console.log(firstColor,endColor);
                    if (firstColor===endColor){
                        // openCardList.push(card2count[0]);
                        // openCardList.push(card2count[1]);
                        card2count = [];

                    }else{
                        flag = false;
                        openCardList.pop();
                        openCardList.pop();
                        setTimeout(()=>{
                            card2count[0].classList.remove("flipped");
                            card2count[1].classList.remove("flipped");

                            card2count = [];
                            flag = true;
                        },500);
                    }
                }
            }
            if(openCardList.length === width*height){
                endTime = new Date();
                console.log(endTime);
                console.log(startTime);
                console.log(endTime-startTime);
                alert("게임 성공 걸린 시간 : "+((endTime-startTime)/1000)+"초");
            }
        }

        console.log(div_flip_card.querySelector(".flip-card-back").style.background);
    });

}
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
        game(div_flip_card);
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

        startTime = new Date();
        console.log(startTime);
    },4000);

}

cardSetting(width, height);
