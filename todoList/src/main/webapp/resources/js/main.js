/* 제목이 작성되지 않은 경우에 form 제출 막기 */


const addForm = document.querySelector("#addForm");
const title = document.querySelector("[name = title]");

// addForm 이 제출 될 때
addForm.addEventListener("submit", e => {
  
  // e : 이벤트 객체 ( 모든 이벤트 정보가 저장되어 있음 )

  // 제목에 입력된 값 가져와서 양쪽 공백 제거
  const input = title.value.trim();


  // 제목이 입력되지 않았을 때
  if(input.length === 0){
    // for 제출 이벤트 막기
    e.preventDefault();

    alert("제목을 입력해 주세요!")
    title.focus();
    
  }


});
























