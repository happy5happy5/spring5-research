let isScrolling = false;

window.addEventListener('wheel', function(event) {
    if (isScrolling) return;
    const deltaY = event.deltaY;
    if (deltaY < 0) {
        // console.log("한 페이지 이전");
        handleAnswerPage('<');
    } else if (deltaY > 0) {
        // console.log("한 페이지 이후");
        handleAnswerPage('>');
    }
    isScrolling = true;
    setTimeout(function() {
        isScrolling = false;
    }, 800);
});

let currentRsiNo = 0;
// 현재 주소가 http://127.0.0.1:9099/research/read/105 이런 형태이면 실행
if(window.location.href.indexOf("research/read/") !== -1){
    document.querySelector(".answer-content-body").innerHTML = createIntroHTML();
}

function handleAnswerPage(direction) {
    // 현재의 답변이 작성 되었는 지 확인
    if(!isAnswered() && direction === '>' && currentRsiNo !== 0){
        alert("답변을 작성해 주세요.");
        return;
    }
    let answerContent = document.querySelector(".answer-content-body");
    if (direction === '>') {
        if (currentRsiNo < rsiList.length) {
            currentRsiNo+=1;
        }
    } else if (direction === '<') {
        if (currentRsiNo > 0) {
            currentRsiNo-=1;
        }
    }
    let type = rsiList[currentRsiNo-1].rsi_type;
    setCurrentQNum();
    answerContent.innerHTML = createAnswerContentHTMLByPage(currentRsiNo);
    //     type 에 따라 답을 여기서 넣어 줘야 한다.
    answerList.forEach((item)=>{
        if(item.no === currentRsiNo){
            setAnswerBySavedValue(answerContent,currentRsiNo)
        }
    })
    // 여기서 이벤트 다는 것이 합리적인 선택 인가?
    setEventListenersOnInputs();

    //     다중 선택일 때 처리 해야 한다.
    if(type === "5"){
        let answerContentBody = document.querySelector(".answer-content-body");
        let choices = answerContentBody.querySelectorAll("input[name='choice']");
        choices.forEach((item)=>{
            item.addEventListener("change",()=>{
                let checkedCount = 0;
                choices.forEach((item)=>{
                    if(item.checked){
                        checkedCount++;
                    }
                })
                if(checkedCount > rsiList[currentRsiNo].rsi_type5){
                    alert("선택 가능한 항목을 초과하였습니다.");
                    item.checked = false;
                }
            })
        })
    }

    if(answerList.length-1 === currentRsiNo){
        let confirms = confirm("설문을 완료 하시겠습니까?");
        console.log(confirms);
        //     여기서 제출 해야함
    }

}

function setEventListenersOnInputs(){
    let answerContent = document.querySelector(".answer-content-body");
    let inputs = answerContent.querySelectorAll("input[name='choice']");
    // todo 주관식은 따로 처리 해야 한다.
    inputs.forEach((item)=>{
        item.addEventListener("change",()=>{
            saveAnswerOnChange();
        })
    })
}

function isAnswered(){
    let isAnswered = false;
    answerList.forEach((item)=>{
        if(item.no === currentRsiNo){
            isAnswered = true;
        }
    })
    return isAnswered;
}

function setAnswerBySavedValue(answerContent,num){
    if(answerList.length > num){
        let type = answerList[num].type
        if(type === "3"){
            answerContent.querySelector("input[name='choice']").value = answerList[num].rsi_answer;
        }
        // todo: 다중 선택 항목은 다른 로직이 필요함
        else{
            answerContent.querySelectorAll("input[name='choice']").forEach((item)=>{
                // 여기서 rsi_answer 의 형태 확인 해야 한다.
                if(item.value === answerList[num].rsi_answer){
                    item.checked = true;
                }
            })
        }
    }
}

function saveAnswerOnChange(){
    let temp = document.querySelector("#question-number");
    let type = temp.querySelector("span").innerText;
    let rsi_answer = saveAnswerByType(type);
    if(rsi_answer === null){
        return false;
    }

    // 여기서 해당 번호가 있으면 업데이트 없다면 생성

    let answerObj = {
        rs_seq: research.rs_seq,
        username: userName,
        no : currentRsiNo,
        type: type,
        rsi_answer: rsi_answer.value,
    }

    let isExist = false;
    answerList.forEach((item,index)=>{
        if(item.no === currentRsiNo){
            item.rsi_answer = rsi_answer.value;
            isExist = true;
            answerList[index]=answerObj;
        }
    })
    if(!isExist) answerList.push(answerObj);
    return true;
}

function saveAnswerByType(type){
    let answer = null;
    let answerContentBody = document.querySelector(".answer-content-body");
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    switch (type) {
        case "0":
            answer = answerContentBody.querySelector("input[name='choice']:checked");
            break;
        case "1":
            answer = answerContentBody.querySelector("input[name='choice']:checked");
            break;
        case "2":
            answer = answerContentBody.querySelector("input[name='choice']:checked");
            break;
        case "3":
            answer = answerContentBody.querySelector("input[name='choice'].value");
            break;
        case "4":
            answer = answerContentBody.querySelector("input[name='choice']:checked");
            break;
        case "5":
            answer = answerContentBody.querySelectorAll("input[name='choice']:checked");
            break;
    }
    return answer;
}

function setCurrentQNum() {
    document.querySelector("#currentQNum").innerText = currentRsiNo;
}

function createIntroHTML() {
    return `
               <div class="d-flex col">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">0</span>
                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <div>${research.rs_title}</div>
                    <div>${research.rs_desc}</div>

                    <div class="d-flex row">
                        <div class="row-cols-6">
                            <div class="col w-75">
                                <div class="col-4">
                                    <span class="text-white">시작일</span>
                                </div>
                                <div class="col-8">
                                    <span>${research.rs_start_date}</span>
                                </div>
                            </div>
                            <div class="col w-75">
                                <div class="col-4">
                                    <span class="text-white">종료일</span>
                                </div>
                                <div class="col-8">
                                    <span>${research.rs_end_date}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                `;
}

function createType0HTML(index) {
    let choices="";
    // rsi_type0_1
    if(rsiList[index].rsi_type0_1 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="1">
                        <span>${rsiList[index].rsi_type0_1}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_2 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="2">
                        <span>${rsiList[index].rsi_type0_2}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_3 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="3">
                        <span>${rsiList[index].rsi_type0_3}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_4 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="4">
                        <span>${rsiList[index].rsi_type0_4}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_5 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="5">
                        <span>${rsiList[index].rsi_type0_5}</span>
                    </div>
                `

    return `
               <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>

                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>
                    <div class="d-flex row">

                `
        +choices+
        `
                    </div>
                </div>
            </div>
                `
}

function createType1HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    return `
               <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>

                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>

                    <div class="d-flex row">
                        <div class="row-cols-6 mt-5">
                            <input type="radio" name="choice" value="1">
                            <span>O</span>
                        </div>
                        <div class="row-cols-6 mt-5">
                            <input type="radio" name="choice" value="2">
                            <span>X</span>
                        </div>
                </div>
            </div>
                `
}

function createType2HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    return `
               <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>
                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>

                    <ul class="question-list m-0 p-0 pt-5 likert">
                        <li>
                          <input type="radio" name="choice" value="1">
                          <label>매우 비동의</label>
                        </li>
                        <li>
                          <input type="radio" name="choice" value="2">
                          <label>비동의</label>
                        </li>
                        <li>
                          <input type="radio" name="choice" value="3">
                          <label>보통</label>
                        </li>
                        <li>
                          <input type="radio" name="choice" value="4">
                          <label>동의</label>
                        </li>
                        <li>
                          <input type="radio" name="choice" value="5">
                          <label>매우 동의</label>
                        </li>
                    </ul>
                </div>
            </div>
                `
}

function createType3HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    return `
              <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>
                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>

                    <div class="d-flex row">
                        <div class="row-cols-6 mt-5">
                            <input type="text" name="choice" value="">
                        </div>
                    </div>
                </div>
            </div>
            `
}

function createType4HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    return `
               <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>
                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>

                    <ul class="question-list m-0 p-0">
                        <span class="star-input">
                          <span class="input">
                            <input type="radio" name="choice" id="p1" value="1"><label for="p1">1</label>
                            <input type="radio" name="choice" id="p2" value="2"><label for="p2">2</label>
                            <input type="radio" name="choice" id="p3" value="3"><label for="p3">3</label>
                            <input type="radio" name="choice" id="p4" value="4"><label for="p4">4</label>
                            <input type="radio" name="choice" id="p5" value="5"><label for="p5">5</label>
                            <input type="radio" name="choice" id="p6" value="6"><label for="p6">6</label>
                            <input type="radio" name="choice" id="p7" value="7"><label for="p7">7</label>
                            <input type="radio" name="choice" id="p8" value="8"><label for="p8">8</label>
                            <input type="radio" name="choice" id="p9" value="9"><label for="p9">9</label>
                            <input type="radio" name="choice" id="p10" value="10"><label for="p10">10</label>
                          </span>
                        </span>
                    </ul>
                </div>
            </div>
            `
}

function createType5HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    let choices="";
    // rsi_type0_1
    if(rsiList[index].rsi_type0_1 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="1">
                        <span>${rsiList[index].rsi_type0_1}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_2 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="2">
                        <span>${rsiList[index].rsi_type0_2}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_3 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="3">
                        <span>${rsiList[index].rsi_type0_3}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_4 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="4">
                        <span>${rsiList[index].rsi_type0_4}</span>
                    </div>
                `
    if(rsiList[index].rsi_type0_5 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="5">
                        <span>${rsiList[index].rsi_type0_5}</span>
                    </div>
                `

    return `
               <div class="d-flex col h-100">
                <div id="question-number" class="col-2 d-flex justify-content-end me-3">
                    <span style="display: none">${rsiList[index].rsi_type}</span>

                    <span class="text-white">설문 내용</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>
                    <span>${rsiList[index].rsi_type5}개 까지 선택가능</span>

                    <div class="d-flex row">

                `
        +choices+
        `
                    </div>
                </div>
            </div>
                `
}

function createAnswerContentHTMLByPage(pageNum) {
    let answerContent;

    if(pageNum === 0){
        return createIntroHTML();
    }

    let index = pageNum-1;
    let type = rsiList[index].rsi_type;

    switch (type) {
        case "0":
            answerContent=createType0HTML(index);
            break;
        case "1":
            answerContent=createType1HTML(index);
            break;
        case "2":
            answerContent=createType2HTML(index);
            break;
        case "3":
            answerContent=createType3HTML(index);
            break;
        case "4":
            answerContent=createType4HTML(index);
            break;
        case "5":
            answerContent=createType5HTML(index);
            break;
    }
    return answerContent;
}
