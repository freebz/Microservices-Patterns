# 예제 12-2 음식점 서비스 컨테이너 이미지를 빌드하는 셸 커맨드

cd ftgo-restaurant-service
../gradlew assemble
docker build -t ftgo-restaurant-service .
