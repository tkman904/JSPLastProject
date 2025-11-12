<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>맛집 예약 UI (Bootstrap)</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body{
      background-color:#f8f9fa;
      font-family:'Noto Sans KR', sans-serif;
    }
    .card{
      box-shadow:0 4px 10px rgba(0,0,0,0.05);
      border:none;
      border-radius:12px;
    }
    .card-header{
      font-weight:700;
      font-size:1.1rem;
    }
    #food_list{
      height:600px;
      overflow-y:auto;
    }
    .food-item:hover{
      background-color:#f1f1f1;
      cursor:pointer;
    }
    img#food_poster{
      border-radius:8px;
    }
  </style>
</head>
<body>
  <div class="container my-4">
    <div class="row g-4">
      <!-- 맛집 정보 -->
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-header bg-danger text-white text-center">맛집 정보</div>
          <div class="card-body" id="food_list">
            <table class="table table-hover align-middle">
              <tbody>
                <tr class="food-item" onclick="selectFood(1)">
                  <td><img src="https://picsum.photos/seed/1/100/80" class="rounded"></td>
                  <td>
                    <strong>홍대 감성 한식집</strong><br>
                    <small class="text-muted">한식 · 평점 4.7</small>
                  </td>
                </tr>
                <tr class="food-item" onclick="selectFood(2)">
                  <td><img src="https://picsum.photos/seed/2/100/80" class="rounded"></td>
                  <td>
                    <strong>강남 스테이크 하우스</strong><br>
                    <small class="text-muted">양식 · 예약 필수</small>
                  </td>
                </tr>
                <tr class="food-item" onclick="selectFood(3)">
                  <td><img src="https://picsum.photos/seed/3/100/80" class="rounded"></td>
                  <td>
                    <strong>이태원 월드푸드 마켓</strong><br>
                    <small class="text-muted">세계요리 · 테이크아웃 가능</small>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- 예약일 정보 -->
      <div class="col-md-5">
        <div class="card h-100">
          <div class="card-header bg-info text-white text-center">예약일 정보</div>
          <div class="card-body text-center" id="food_rdays">
            <p class="text-muted">날짜를 선택하세요</p>
            <div class="d-flex justify-content-center flex-wrap gap-2">
              <button class="btn btn-outline-info btn-sm" onclick="pickDay('2025-11-18')">2025-11-18</button>
              <button class="btn btn-outline-info btn-sm" onclick="pickDay('2025-11-19')">2025-11-19</button>
              <button class="btn btn-outline-info btn-sm" onclick="pickDay('2025-11-20')">2025-11-20</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 예약 정보 -->
      <div class="col-md-3">
        <div class="card h-100">
          <div class="card-header bg-success text-white text-center">예약 정보</div>
          <div class="card-body text-center">
            <img id="food_poster" src="https://picsum.photos/seed/10/250/180" class="img-fluid mb-3" alt="poster">
            <table class="table table-borderless text-start">
              <tbody>
                <tr><td class="text-muted">업체명</td><td id="food_name">-</td></tr>
                <tr><td class="text-muted">예약일</td><td id="food_reserve_day">-</td></tr>
                <tr><td class="text-muted">예약시간</td><td id="food_reserve_time">-</td></tr>
                <tr><td class="text-muted">예약인원</td><td id="food_reserve_inwon">-</td></tr>
              </tbody>
            </table>
            <form method="post" action="../reserve/reserve_insert.do" id="reserveBtn" style="display:none">
              <input type="hidden" name="fno" id="rfno">
              <input type="hidden" name="day" id="rday">
              <input type="hidden" name="time" id="rtime">
              <input type="hidden" name="inwon" id="rinwon">
              <button type="submit" class="btn btn-primary w-100">예약하기</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div class="row g-4 mt-3">
      <div class="col-md-4">
        <div class="card">
          <div class="card-header bg-primary text-white text-center">시간 정보</div>
          <div class="card-body text-center" id="reserve_time2">
            <div class="d-flex justify-content-center gap-2 flex-wrap">
              <button class="btn btn-outline-primary btn-sm">11:00</button>
              <button class="btn btn-outline-primary btn-sm">12:00</button>
              <button class="btn btn-outline-primary btn-sm">18:00</button>
            </div>
          </div>
        </div>
      </div>

      <div class="col-md-8">
        <div class="card">
          <div class="card-header bg-info text-white text-center">인원 정보</div>
          <div class="card-body text-center" id="reserve_inwon">
            <div class="d-flex justify-content-center gap-2 flex-wrap">
              <button class="btn btn-outline-success btn-sm" onclick="pickInwon(2)">2명</button>
              <button class="btn btn-outline-success btn-sm" onclick="pickInwon(4)">4명</button>
              <button class="btn btn-outline-success btn-sm" onclick="pickInwon(6)">6명</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
    function selectFood(id){
      const data={
        1:{name:'홍대 감성 한식집',poster:'https://picsum.photos/seed/101/500/300'},
        2:{name:'강남 스테이크 하우스',poster:'https://picsum.photos/seed/102/500/300'},
        3:{name:'이태원 월드푸드 마켓',poster:'https://picsum.photos/seed/103/500/300'}
      };
      const d=data[id];
      document.getElementById('food_name').innerText=d.name;
      document.getElementById('food_poster').src=d.poster;
      document.getElementById('rfno').value=id;
      document.getElementById('reserveBtn').style.display='block';
    }
    function pickDay(day){
      document.getElementById('food_reserve_day').innerText=day;
      document.getElementById('rday').value=day;
    }
    function pickInwon(n){
      document.getElementById('food_reserve_inwon').innerText=n+'명';
      document.getElementById('rinwon').value=n;
    }
  </script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>