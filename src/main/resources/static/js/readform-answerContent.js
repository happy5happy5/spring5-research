let isScrolling = false;

window.addEventListener('wheel', function (event) {
    if (isScrolling) return;
    const deltaY = event.deltaY;
    if (deltaY < 0) {
        handleAnswerPage('<');
    } else if (deltaY > 0) {
        handleAnswerPage('>');
    }
    isScrolling = true;
    setTimeout(function () {
        isScrolling = false;
    }, 800);
});

let currentRsiNo = 0;
// 현재 주소가 http://127.0.0.1:9099/research/read/105 이런 형태이면 실행
if (window.location.href.indexOf("research/read/") !== -1) {
    document.querySelector(".answer-content-body").innerHTML = createIntroHTML();
}

function handleAnswerPage(direction) {
    let currentPageNum = document.querySelector('#question-number').nextElementSibling.querySelector("span").innerText;
    // 현재의 답변이 작성 되었는 지 확인
    if (!isAnswered() && direction === '>' && currentRsiNo !== 0) {
        pageButtonController()
        alert("답변을 작성해 주세요.");
        return;
    }
    let answerContent = document.querySelector(".answer-content-body");
    if (direction === '>') {
        if (currentRsiNo < rsiList.length) {
            currentRsiNo += 1;
        }
    } else if (direction === '<') {
        if (currentRsiNo > 0) {
            currentRsiNo -= 1;
        }
    }
    setCurrentQNum();
    answerContent.innerHTML = createAnswerContentHTMLByPage(currentRsiNo);
    if (currentRsiNo === 0) {
        pageButtonController()
        return;
    }
    let type = rsiList[currentRsiNo - 1].rsi_type;
    //     type 에 따라 답을 여기서 넣어 줘야 한다.
    answerList.forEach((item) => {
        if (item.no === currentRsiNo) {
            setAnswerBySavedValue(answerContent, currentRsiNo)
        }
    })
    // 여기서 이벤트 다는 것이 합리적인 선택 인가?
    if (type === "3") {
        setEventListenersOnInputs(3);
    }
    {
        setEventListenersOnInputs();
    }

    //     다중 선택일 때 처리 해야 한다.
    if (type === "5") {
        let answerContentBody = document.querySelector(".answer-content-body");
        let choices = answerContentBody.querySelectorAll("input[name='choice']");
        choices.forEach((item) => {
            item.addEventListener("change", () => {
                let checkedCount = 0;
                choices.forEach((item) => {
                    if (item.checked) {
                        checkedCount++;
                    }
                })
                if (checkedCount > rsiList[currentRsiNo - 1].rsi_type5) {
                    alert("선택 가능한 항목을 초과하였습니다.");
                    item.checked = false;
                }
            })
        })
    }

    if (currentRsiNo === rsiList.length && direction === '>' && answerList.length-1 === rsiList.length && currentPageNum === "Q"+currentRsiNo) {

        let isAllAnswered = true;
        answerList.forEach((item,index) => {
            if(index===0) return;
            if (item.rsi_answer === null||item.rsi_answer===""||item.rsi_answer===undefined) {
                isAllAnswered = false;
            }
        })
        if (!isAllAnswered) {
            alert("답변을 모두 작성해 주세요.");
            return;
        }

        let submit = confirm("제출 하시겠습니까?");
        if (submit) {
            let data = {
                rs_seq: research.rs_seq,
                username: userName,
                answerList: answerList.slice(1),
            }
            axios.post("/research/response", data)
                .then((res) => {
                    console.log(res)
                    if (res.data.message === "success") {
                        alert("제출 되었습니다.");
                        window.location.href = "/research/list";
                    }
                })
                .catch((err) => {
                    console.log(err);
                })
        }

    }

    pageButtonController();

}
function setEventListenersOnInputs(flag) {
    let answerContent = document.querySelector(".answer-content-body");
    if (flag === 3) {
        let textarea = answerContent.querySelector("textarea[name='choice']");
        textarea.addEventListener("input", () => {
            saveAnswerOnChange();
        })

    } else {
        let inputs = answerContent.querySelectorAll("input[name='choice']");
        inputs.forEach((item) => {
            item.addEventListener("change", () => {
                saveAnswerOnChange();
            })
        })
    }
}

function isAnswered() {
    let isAnswered = false;
    answerList.forEach((item) => {
        if (item.no === currentRsiNo) {
            if(item.rsi_answer!==null) {
                // console.log(item.rsi_answer)
                isAnswered = true;
            }
        }
    })
    return isAnswered;
}

function setAnswerBySavedValue(answerContent, num) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    if (answerList.length > num) {
        let type = answerList[num].type
        if (type === "3") {
            answerContent.querySelector("textarea[name='choice']").value = answerList[num].rsi_answer;
        } else if (type === "5") {
            answerList[num].rsi_answer.split(",").forEach((item) => {
                answerContent.querySelectorAll("input[name='choice']").forEach((input) => {
                    if (input.value === item) {
                        input.checked = true;
                    }
                })
            })
        } else {
            answerContent.querySelectorAll("input[name='choice']").forEach((item) => {
                // 여기서 rsi_answer 의 형태 확인 해야 한다.
                if (item.value === answerList[num].rsi_answer) {
                    item.checked = true;
                }
            })
        }
    }
}

function saveAnswerOnChange() {
    let temp = document.querySelector("#question-number");
    let type = temp.querySelector("span").innerText;
    let rsi_answer = saveAnswerByType(type);
    if (rsi_answer === null) return;

    // 여기서 해당 번호가 있으면 업데이트 없다면 생성

    let answerObj = {
        rs_seq: research.rs_seq,
        username: userName,
        no: currentRsiNo,
        type: type,
        rsi_answer: rsi_answer,
    }

    let isExist = false;
    answerList.forEach((item, index) => {
        if (item.no === currentRsiNo) {
            item.rsi_answer = rsi_answer.value;
            isExist = true;
            answerList[index] = answerObj;
        }
    })
    if (!isExist) answerList.push(answerObj);
    return true;
}

function saveAnswerByType(type) {
    let answer = null;
    let answerContentBody = document.querySelector(".answer-content-body");
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    switch (type) {
        case "0":
            answer = answerContentBody.querySelector("input[name='choice']:checked").value;
            break;
        case "1":
            answer = answerContentBody.querySelector("input[name='choice']:checked").value;
            break;
        case "2":
            answer = answerContentBody.querySelector("input[name='choice']:checked").value;
            break;
        case "3":
            answer = answerContentBody.querySelector("textarea[name='choice']").value;
            break;
        case "4":
            answer = answerContentBody.querySelector("input[name='choice']:checked").value;
            break;
        case "5":
            answer = answerContentBody.querySelectorAll("input[name='choice']:checked");
            answer = Array.from(answer).map((item) => {
                return item.value;
            })
            answer = answer.join(",");
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
                    <span class="text-white">설문 제목</span>
                    <span>-></span>
                </div>

                <div class="col-10 pe-5 h-100">
                    <div>${research.rs_title}</div>
                    <hr>
                    <div class="mb-3">${research.rs_desc}</div>

                    <div class="d-flex row">
                        <div class="row-cols-6">
                            <span class="col w-75">
                                <span class="col-4">
                                    <span class="text-white">시작일</span>
                                </span>
                                <span class="col-8">
                                    <span>${research.rs_start_date}</span>
                                </span>
                            </span>
                            <span class="col w-75">
                                <span class="col-4">
                                    <span class="text-white">종료일</span>
                                </span>
                                <span class="col-8">
                                    <span>${research.rs_end_date}</span>
                                </span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
                <div class="col-12 d-flex justify-content-end me-3">
<!--                    <button class="btn btn-outline-primary" onclick="handleAnswerPage('<')">이전</button>-->
                    <button class="btn btn-outline-primary pagebutton-enter" onclick="handleAnswerPage('>')">참여</button>
                </div>
                `;
}

function createType0HTML(index) {
    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    let choices = "";
    // rsi_type0_1
    if (rsiList[index].rsi_type0_1 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="1">
                        <span>${rsiList[index].rsi_type0_1}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_2 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="2">
                        <span>${rsiList[index].rsi_type0_2}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_3 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="3">
                        <span>${rsiList[index].rsi_type0_3}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_4 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="radio" name="choice" value="4">
                        <span>${rsiList[index].rsi_type0_4}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_5 !== null)
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

                    
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>
                    <div class="d-flex row">

                `
        + choices
        +
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
                    
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>
                    <div class="question-list m-0 p-0">
                        <textarea placeholder="주관식 답변" name="choice" oninput="autoResizeTextArea(this)"></textarea>
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
    let choices = "";
    // rsi_type0_1
    if (rsiList[index].rsi_type0_1 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="1">
                        <span>${rsiList[index].rsi_type0_1}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_2 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="2">
                        <span>${rsiList[index].rsi_type0_2}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_3 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="3">
                        <span>${rsiList[index].rsi_type0_3}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_4 !== null)
        choices += `
                    <div class="row-cols-6 mt-5">
                        <input type="checkbox" name="choice" value="4">
                        <span>${rsiList[index].rsi_type0_4}</span>
                    </div>
                `
    if (rsiList[index].rsi_type0_5 !== null)
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

                    
                </div>

                <div class="col-10 pe-5">
                    <span>Q${currentRsiNo}</span>
                    <span>${rsiList[index].rsi_question}</span>
                    <small>(${rsiList[index].rsi_type5}개 까지 선택가능)</small>

                    <div class="d-flex row">

                `
        + choices +
        `
                    </div>
                </div>
            </div>
                `
}

function createAnswerContentHTMLByPage(pageNum) {
    let answerContent;

    if (pageNum === 0) {
        return createIntroHTML();
    }

    let index = pageNum - 1;
    let type = rsiList[index].rsi_type;

    switch (type) {
        case "0":
            answerContent = createType0HTML(index);
            break;
        case "1":
            answerContent = createType1HTML(index);
            break;
        case "2":
            answerContent = createType2HTML(index);
            break;
        case "3":
            answerContent = createType3HTML(index);
            break;
        case "4":
            answerContent = createType4HTML(index);
            break;
        case "5":
            answerContent = createType5HTML(index);
            break;
    }
    return answerContent;
}
pageButtonController()
function pageButtonController(){
    let pageButtonEnter = document.querySelector(".pagebutton-enter");
    // let pageButtonSubmit = document.querySelector(".pagebutton-submit");
    let pageButtonPrev = document.querySelector(".pagebutton-prev");
    let pageButtonNext = document.querySelector(".pagebutton-next");

    // console.log(currentRsiNo)
    // console.log(rsiList.length)
    // visibility
    if(currentRsiNo===0){
        pageButtonEnter.style.visibility = "visible";
        pageButtonPrev.style.visibility = "hidden";
        pageButtonNext.style.visibility = "hidden";
        // pageButtonSubmit.style.visibility = "hidden";
        return;
    }
    if(currentRsiNo===rsiList.length){
        // pageButtonEnter.style.visibility = "hidden";
        pageButtonPrev.style.visibility = "visible";
        pageButtonNext.style.visibility = "visible";
        pageButtonNext.innerHTML = "제출";
        // pageButtonSubmit.style.visibility = "visible";
        return;
    }

    if(currentRsiNo<rsiList.length){
        // pageButtonEnter.style.visibility = "hidden";
        pageButtonPrev.style.visibility = "visible";
        pageButtonNext.style.visibility = "visible";
        pageButtonNext.innerHTML = "다음 문제";
        return;
    }
}

