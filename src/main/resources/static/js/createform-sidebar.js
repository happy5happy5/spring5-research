function handleSidebarSubmitButtonClick(element) {
    const sidebarList = document.querySelector('.sidebar').children;
    const currentElNum = document.querySelector('#layout-content #question-number span').textContent;
    console.log("[handleSidebarSubmitButtonClick] currentElNum : " + currentElNum)
    saveContentAction(sidebarList, currentElNum);


    const surveyData = {
        // title: surveyTitle,
        // description: surveyDescription,
        content: []
    }
    for (let i = 0; i < sidebarList.length-1; i++) {
        // -1 해주는 이유는 footer 의 존재 때문
        const type = sidebarList[i].querySelector('.saveType').value;
        const data = sidebarList[i].querySelector('.saveData').value;
        const html = sidebarList[i].querySelector('.saveHTML').value;
        const data_input = sidebarList[i].querySelector('.saveData_input').value;
        const content = {
            type: type,
            data_textarea: data,
            html: html,
            data_input: data_input
        }
        surveyData.content.push(content);
    }
    console.log(surveyData);
}

function handleSideBarCreatButtonByType(element) {
    let currentElNum = document.querySelector('#layout-content #question-number span').textContent;
    const sidebarList = document.querySelector('.sidebar').children;
    saveContentAction(sidebarList, currentElNum);
    const currentLiEl = element.closest('.sidebar-item');
    const parentUlEl = currentLiEl.closest('.sidebar');
    // UlEl의 parentLiEl의 index를 찾습니다.
    const index = Array.prototype.indexOf.call(parentUlEl.children, currentLiEl);
    if(element.dataset.type === 'delete'){
        // 삭제
        if(currentLiEl.querySelector('.item-numbering').textContent === 'Q0'){
            alert('기본 정보 입력란은 삭제 할 수 없습니다.')
            return;
        }
        parentUlEl.removeChild(currentLiEl);
        setQNumWhenDeleteOnSideBar(parentUlEl);
        loadContentAction(parentUlEl.children, index - 1);
        return;
    }


    // parentUlEl 의 index+1 번째에 새로운 li 요소를 만듭니다.
    const type = element.dataset.type;
    // newLiEL 에 type 따라서 다른 html 넣어줍니다.
    for (let i = 0; i < sidebarList.length; i++) {
        if (i === 0) continue;
        addDragAndDropEvent(sidebarList[i], "S");
    }
    createSideBarElementByType(parentUlEl, index, '', type);
    setQNumWhenAddOnSideBar(parentUlEl);

}

function createSideBarElementByType(parentUlEl, index, title, type) {
    //     sidebar-item 를 만든다
    //     리터럴 형식으로
    //     타입에 따라 다른 생성 방식 필요
    let newLiElinSideBar =
        `
            <li class="sidebar-item" draggable="true" onclick="handleSidebarItemClick(this)">
                <div data-input="data" class="data-type-input">

                    <!--                    여기에 필요 한 모든 입력값 저장 한다-->
                    <!--                    0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택 -->
                    <div class="saveType" style="display: none" value=""></div>
                    <div class="saveData" style="display: none" value=""></div>
                    <div class="saveData_input" style="display: none"></div>
                    <div class="saveHTML" style="display: none" value=""></div>
                </div>

                <div class="nav-link text-white col ps-0 d-flex justify-content-between align-items-center" aria-current="page">
                    <span class="item-numbering"></span>
                    <span class="item-title ms-2">${title}</span>
                    <div class="btn-group" onclick="stopPropagation(event)">
                        <!-- popper button -->
                        <div type="button" class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" onclick="stopPropagation(event)">
                            <i></i>
                        </div>
                        <ul class="dropdown-menu" onclick="stopPropagation(event)">
                            <li>
                                <div class="ms-3">기능</div>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <!--                         0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택        -->
                            <li>
                                <div class="dropdown-item" data-type="0" onclick="handleSideBarCreatButtonByType(this)">객관식</div>
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="1" onclick="handleSideBarCreatButtonByType(this)">OX</div>
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="2" onclick="handleSideBarCreatButtonByType(this)">LIKERT</div>
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="3" onclick="handleSideBarCreatButtonByType(this)">주관식</div>
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="4" onclick="handleSideBarCreatButtonByType(this)">별점</div>
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="5" onclick="handleSideBarCreatButtonByType(this)">다중선택 객관식</div>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <div class="dropdown-item" data-type="delete" onclick="handleSideBarCreatButtonByType(this)">삭제</div>
                            </li>

                        </ul>
                    </div>
                </div>
            </li>
            `
    newLiElinSideBar = htmlToElement(newLiElinSideBar);
    addDragAndDropEvent(newLiElinSideBar, "S");
    console.log("[createSideBarElementByType] type : " + type)
    newLiElinSideBar.querySelector('.saveType').value = type;
    // 기본값 저장
    newLiElinSideBar.querySelector('.saveHTML').value = `
        <div class="text-info">
            Main Content
        </div>
        <div class="main-content d-flex p-0 m-0 pt-3">
        `
        +
        createMainContentElementByType(index, title, type).outerHTML
        +
        `
        </div>

        <!--    토글 버튼-->
        <button class="sidebar-toggle shadow-none bg-transparent btn btn-toolbar" onclick="toggleAnimation(this)">
            <img alt="" src="/img/white-burger-menu-icon.png" width="10px" height="10px">
        </button>

            `;
    // default 값 저장 (textarea 전용) switch 문으로
    // 나중에 input 창 데이터 저장 하는 것도 만들어야 함
    // input 초기값 수정 필요
    <!--                    0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택 -->

    switch (type){
        case '0' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify([""]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify([""]);
            break;
        case '1' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify(["","O","X"]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify([""]);
            break;
        case '2' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify(["","","","",""]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify([""]);
            break;
        case '3' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify(["",""]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify([""]);
            break;
        case '4' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify([""]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify([""]);
            break;
        case '5' :
            newLiElinSideBar.querySelector('.saveData').value = JSON.stringify([""]);
            newLiElinSideBar.querySelector('.saveData_input').value = JSON.stringify(["",""]);
            break;
    }

    parentUlEl.insertBefore(newLiElinSideBar, parentUlEl.children[index + 1]);
    loadContentAction(parentUlEl.children, index + 1);
}

function createMainContentElementByType(index,title,type){
    let newMainContentEl = '';
    switch (type)
    {
        case '0' :
            newMainContentEl = createMainContentElementType0(index,title,type);
            break;
        case '1' :
            newMainContentEl = createMainContentElementType1(index,title,type);
            break;
        case '2' :
            newMainContentEl = createMainContentElementType2(index,title,type);
            break;
        case '3' :
            newMainContentEl = createMainContentElementType3(index,title,type);
            break;
        case '4' :
            newMainContentEl = createMainContentElementType4(index,title,type);
            break;
        case '5' :
            newMainContentEl = createMainContentElementType5(index,title,type);
            break;
    }
    return newMainContentEl;
}

// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType0(index, title, type) {
    // newMainContentEl = htmlToElement(newMainContentEl);
    // addDragAndDropEvent(newMainContentEl,"Q");
    // let mainContentDiv = document.querySelector('#layout-content .main-content')
    // console.log("mainContentDiv")
    // console.log(mainContentDiv)
    // todo: 여기에서 애니메이션 용 클래스를 추가
    //     mainContentDiv 의 내용 삭제 후 newMainContentEl 을 추가 합니다.
    // mainContentDiv.innerHTML = '';
    // mainContentDiv.appendChild(newMainContentEl);
    let newMainContentEl = `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>

                    <ul class="question-list m-0 p-0">

                    </ul>

                    <div class="main-content-add-question" onclick="handleQuestionAddButtonOnMainContent(this)">질문 추가</div>

                </div>
            </div>
            `;
    newMainContentEl = htmlToElement(newMainContentEl);
    addDragAndDropEvent(newMainContentEl, "Q");
    return newMainContentEl;
}

// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType1(index, title, type) {
    let newMainContentEl =
        `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>

                    <ul class="question-list m-0 p-0">
                        <li class="question-item d-flex col justify-content-evenly" >
                            <div class="item-numbering col-1">1</div>
                            <textarea class="col-8" readonly>O</textarea>
                        </li>
                        <li class="question-item d-flex col justify-content-evenly" >
                            <div class="item-numbering col-1">2</div>
                            <textarea class="col-8" readonly>X</textarea>
                        </li>
                    </ul>
                </div>
            </div>


            `
    newMainContentEl = htmlToElement(newMainContentEl);
    // addDragAndDropEvent(newMainContentEl, "Q");
    return newMainContentEl;
}
// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType2(index, title, type) {
    let newMainContentEl =
        `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5 ">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>

                    <ul class="question-list m-0 p-0 likert">
                        <li>
                          <input type="radio" name="likert" value="1">
                          <label>매우 비동의</label>
                        </li>
                        <li>
                          <input type="radio" name="likert" value="2">
                          <label>비동의</label>
                        </li>
                        <li>
                          <input type="radio" name="likert" value="3">
                          <label>보통</label>
                        </li>
                        <li>
                          <input type="radio" name="likert" value="4">
                          <label>동의</label>
                        </li>
                        <li>
                          <input type="radio" name="likert" value="5">
                          <label>매우 동의</label>
                        </li>
                    </ul>
                </div>
            </div>

            `
    newMainContentEl = htmlToElement(newMainContentEl);
    return newMainContentEl;
}
// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType3(index, title, type) {
    let newMainContentEl =
        `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>

                    <div class="question-list m-0 p-0">
                        <textarea placeholder="주관식 답변" oninput="autoResizeTextArea(this)" readonly></textarea>
                    </div>
                </div>
            </div>

            `
    newMainContentEl = htmlToElement(newMainContentEl);
    return newMainContentEl;
}
// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType4(index, title, type) {
    let newMainContentEl =
        `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>

                    <ul class="question-list m-0 p-0">
                        <span class="star-input">
                          <span class="input">
                            <input type="radio" name="star-input" id="p1" value="1"><label for="p1">1</label>
                            <input type="radio" name="star-input" id="p2" value="2"><label for="p2">2</label>
                            <input type="radio" name="star-input" id="p3" value="3"><label for="p3">3</label>
                            <input type="radio" name="star-input" id="p4" value="4"><label for="p4">4</label>
                            <input type="radio" name="star-input" id="p5" value="5"><label for="p5">5</label>
                            <input type="radio" name="star-input" id="p6" value="6"><label for="p6">6</label>
                            <input type="radio" name="star-input" id="p7" value="7"><label for="p7">7</label>
                            <input type="radio" name="star-input" id="p8" value="8"><label for="p8">8</label>
                            <input type="radio" name="star-input" id="p9" value="9"><label for="p9">9</label>
                            <input type="radio" name="star-input" id="p10" value="10"><label for="p10">10</label>
                          </span>
                        </span>
                    </ul>
                </div>
            </div>
            `
    newMainContentEl = htmlToElement(newMainContentEl);
    return newMainContentEl;
}
// 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
function createMainContentElementType5(index, title, type) {
    let newMainContentEl =
        `
            <div class="d-flex col">
                <div id="question-number" class="col-1 d-flex justify-content-end me-3">
                    <span>${index + 1}</span>
                    <span>-></span>
                </div>

                <div class="col-11 pe-5">
                    <textarea placeholder="질문을 입력하세요" oninput="handleTextAreaInput(this,'Q')">${title}</textarea>
                    
<!--                    숫자 입력만 넣을 수 있는 1의 기본 값을 가지고 최대 question-list의 개수만큼 지정 할수 있는 인풋 만들기-->
<div>
최대 질문 개수 설정
<input type="number" min="1" max="5" value="1">
</div>

                    <ul class="question-list m-0 p-0">

                    </ul>

                    <div class="main-content-add-question" onclick="handleQuestionAddButtonOnMainContent(this)">질문 추가</div>

                </div>
            </div>
            `
    newMainContentEl = htmlToElement(newMainContentEl);

    return newMainContentEl;
}
