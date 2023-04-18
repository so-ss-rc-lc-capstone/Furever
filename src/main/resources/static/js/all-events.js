//
// (function(async) {
//     document.getElementById('event-feed')
//     let cards;
//     // Code to be executed immediately
//     getAllEvents()
// function getAllEvents() {
// fetch('http://localhost:8080/api/allevents', {
//     method: "GET",
//     headers: {
//         "Content-Type": "application/json",
//     }
// })
//     .then(response => response.json())
//     .then(data => {
//         // console.log(data);
//         cards = JSON.stringify(data)
//         console.log(cards)
//         })
//
// }
//
//
// function populateCards(cards){
//         let allEvents = JSON.parse(cards);
//         let html ="";
//     for (let i = 0; i <allEvents.length; i++){
//         html += `
//         <!-- User POSTS -->
//
// <div class="flex flex-col mt-[4em] justify-between border w-full h-[25em] mb-5 pb-5 border-gray-200 rounded-lg shadow dark:border-gray-700"
//     style="'background-image:url(\\'' + allEvents.eventPhoto + '\\'); background-size:cover; background-position:center;'">
//
//
//     <div class=" w-full mt-[10px] h-[3em]">
//         <div class="w-full flex items-center justify-between">
//
//
//             <form th:action="@{'/events/' + ${allEvents.id} + '/participate'}" method="post">
//                 <button id="color-toggle" type="submit"
//                         class="bg-white w-[150px] duration-75 h-[45px] ml-[4em] text-black font-medium rounded-lg text-sm px-5 py-2.5 text-center"
//                         th:classappend="${event.hasParticipated(#authentication.principal.id) ? 'bg-white text-black' : ''}">
//                     <span th:text="${event.hasParticipated(#authentication.principal.id) ? 'Attending' : 'Participate'}"></span>
//                 </button>
//             </form>
//
//
//             <!--Delete btn-->
//             <div class="w-full flex justify-end" th:if="${event.user.id} == ${#authentication.principal.id}">
//                 <form th:action="@{/events/{id}/delete(id=${event.id})}" method="get">
//                     <input type="hidden" name="delete_id" th:value="${event.id}"/>
//                     <button type="submit"
//                             class="flex items-center mr-[4em] justify-center text-white h-12 w-[60px] rounded-lg bg-red-600 hover:bg-red-800 font-medium text-sm text-center inline-flex items-center dark:bg-blue-600">
//                         <h2 class="text-white">Delete</h2>
//                     </button>
//                 </form>
//             </div>
//
//
//             <div th:if="${event.user.id} != ${#authentication.principal.id}">
//                 <button type="button"
//                         class="flex items-center mr-[4em] justify-center text-white h-12 w-12 bg-white hover:bg-gray-100 font-medium rounded-full text-sm text-center inline-flex items-center dark:bg-blue-600">
//                     <svg class="black w-5" fill="none" stroke="black" stroke-width="1.5" viewBox="0 0 24 24"
//                          xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
//                         <path stroke-linecap="round" stroke-linejoin="round"
//                               d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z"></path>
//                     </svg>
//                 </button>
//             </div>
//
//         </div>
//     </div>
//
//
//     <div class=" flex justify-center items-center w-full h-[10em]">
//         <!--Event Description-->
//         <div class="w-full md:w-4/5 h-full bg-white rounded-xl">
//
//             <div class="flex flex-row w-full h-2/3 justify-between">
//                 <div class="w-2/3">
//                     <div class="w-full">
//                         <h2 class="text-2xl font-bold pl-3 pt-1 pr-3" th:text="${event.title}"></h2>
//                         <div class="text-sm pl-3 pr-3 pb-3">
//
//                             <p class="text-[14px]" th:text="${#temporals.format(event.event_DateAndTime, 'dd-MMM-yyyy h:mm a')}"></p>
// <!--                            <p class="text-[10px] flex"><span>-->
// <!--                              <svg fill="none" class="h-[13px] mt-1" stroke="currentColor" stroke-width="1.5"-->
// <!--                                   viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">-->
// <!--                              <path stroke-linecap="round" stroke-linejoin="round"-->
// <!--                                    d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z"></path>-->
// <!--                              <path stroke-linecap="round" stroke-linejoin="round"-->
// <!--                                    d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z"></path>-->
// <!--                            </svg></span>10 Miles Away-->
// <!--                            </p>-->
//                         </div>
//
//
//                     </div>
//
//                 </div>
//                 <div class="flex pt-3 pr-3 ">
//                     <div class="view-participants-btn w-1/3 h-1/3 text-[10px] flex items-center mr-4"><h3 class="text-blue-600">
//                         <a class="view-participants-btn hover:cursor-pointer" data-modal-target="defaultModal" data-modal-toggle="defaultModal" th:data-event-id="${event.id}">Participants</a>
//                     </h3></div>
//
//
//
//                     <div class="flex h-1/3 -space-x-4">
//                         <a th:each="participation, i : ${event.participations}" th:if="${i.index &lt; 3}">
//                             <img class="w-9 h-9 border-2 border-white rounded-full dark:border-gray-800"
//                                  th:if="${not #strings.isEmpty(participation.user.profilePhoto)}"
//                                  th:src="${participation.user.profilePhoto}" alt="Profile image"/>
//                             <img class="w-9 h-9 border-2 border-white rounded-full dark:border-gray-800"
//                                  th:unless="${not #strings.isEmpty(participation.user.profilePhoto)}"
//                                  th:src="@{/img/profile.jpeg}" alt="Default profile image"/>
//                         </a>
//
//                         <a class="flex items-center justify-center w-9 h-9 text-xs font-medium text-white bg-gray-700 border-2 border-white rounded-full hover:bg-gray-600 dark:border-gray-800"
//                            href="#"><span th:text="${event.participations.size}"></span></a>
//                     </div>
//                 </div>
//
//
//             </div>
//             <div class="w-full h-1/3">
//                 <div class="flex pl-3 items-center space-x-4 justify-between">
//                     <div class="flex ">
//                         <img class="w-10 h-10 rounded-full mr-3" th:src="${#strings.isEmpty(event.user.profilePhoto) ? '/img/profile.jpeg' : event.user.profilePhoto}">
//                         <div class="font-medium dark:text-white">
//                             <div><span th:text="${event.user.getUsername()}"></span></div>
//                             <div class="text-[10px] text-gray-500 dark:text-gray-400"
//                             ><h4>Created at <span class="text-[10px] text-gray-500 dark:text-gray-400"
//                                                th:text="${#temporals.format(event.created_at, 'dd-MMM-yyyy h:mm a')}"></span></h4></div>
//                         </div>
//
//
//
//                     </div>
//                     <form th:action="@{/events/{id}/edit(id=${event.id})}" method="get">
//                         <div th:if="${event.user.id} == ${#authentication.principal.id}">
//                             <input type="hidden" name="edit_id" th:value="${event.id}"/>
//                             <button type="submit"
//                                     class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-3 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
//                                 Edit
//                             </button>
//                         </div>
//                     </form>
//                     <div>
//                         <form th:action="@{/events/{id}/find (id=${event.id})}" method="get">
//                             <input type="hidden" name="event" th:value="${event.id}"/>
//                             <button type="submit"
//                                     class="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 mr-3 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
//                                 See Details
//                             </button>
//                         </form>
//
//                     </div>
//                 </div>
//
//             </div>
//         </div>
//
//     </div>
// </div>
//
//         `
//     }
// }
//
// })();
//