//TMDB 

const API_KEY = 'api_key=1cf50e6248dc270629e802686245c2c8';
const BASE_URL_TV = 'https://api.themoviedb.org/3';
const API_URL_TV = BASE_URL_TV + '/discover/tv?sort_by=popularity.desc&'+API_KEY;
const IMG_URL = 'https://image.tmdb.org/t/p/w500';
const searchURL_TV = BASE_URL_TV + '/search/tv?'+API_KEY;


const genresTv = [
	{
	"id":10759,
	"name":"Action & Adventure"
	},
	{
	"id":16,
	"name":"Animation"
	},
	{
	"id":35,
	"name":"Comedy"
	},
	{
	"id":80,
	"name":"Crime"
	},
	{
	"id":99,
	"name":"Documentary"
	},
	{
	"id":18,
	"name":"Drama"
	},
	{
	"id":10751,
	"name":"Family"
	},
	{
	"id":10762,
	"name":"Kids"
	},
	{
	"id":9648,
	"name":"Mystery"
	},
	{
	"id":10763,
	"name":"News"
	},
	{
	"id":10764,
	"name":"Reality"
	},
	{
	"id":10765,
	"name":"Sci-Fi & Fantasy"
	},
	{
	"id":10766,
	"name":"Soap"
	},
	{
	"id":10767,
	"name":"Talk"
	},
	{
	"id":10768,
	"name":"War & Politics"
	},
	{
	"id":37,
	"name":"Western"
	}
 ]

const main = document.getElementById('main');
const form =  document.getElementById('form');
const search = document.getElementById('search');
const tagsEl = document.getElementById('tags');

const prev = document.getElementById('prev')
const next = document.getElementById('next')
const current = document.getElementById('current')

var currentPage = 1;
var nextPage = 2;
var prevPage = 3;
var lastUrl = '';
var totalPages = 100;

var selectedGenre = []
setGenreTv();
function setGenreTv() {
    tagsEl.innerHTML= '';
    genresTv.forEach(genre => {
        const t = document.createElement('div');
        t.classList.add('tag');
        t.id=genre.id;
        t.innerText = genre.name;
        t.addEventListener('click', () => {
            if(selectedGenre.length == 0){
                selectedGenre.push(genre.id);
            }else{
                if(selectedGenre.includes(genre.id)){
                    selectedGenre.forEach((id, idx) => {
                        if(id == genre.id){
                            selectedGenre.splice(idx, 1);
                        }
                    })
                }else{
                    selectedGenre.push(genre.id);
                }
            }
            console.log(selectedGenre)
            getTvshows(API_URL_TV + '&with_genres='+encodeURI(selectedGenre.join(',')))
            highlightSelectionTv()
        })
        tagsEl.append(t);
    })
}

function highlightSelectionTv() {
    const tags = document.querySelectorAll('.tag');
    tags.forEach(tag => {
        tag.classList.remove('highlight')
    })
    clearBtnTv()
    if(selectedGenre.length !=0){   
        selectedGenre.forEach(id => {
            const hightlightedTag = document.getElementById(id);
            hightlightedTag.classList.add('highlight');
        })
    }

}

function clearBtnTv(){
    let clearBtnTv = document.getElementById('clear');
    if(clearBtnTv){
        clearBtnTv.classList.add('highlight')
    }else{
            
        let clear = document.createElement('div');
        clear.classList.add('tag','highlight');
        clear.id = 'clear';
        clear.innerText = 'Clear x';
        clear.addEventListener('click', () => {
            selectedGenre = [];
            setGenreTv();            
            getTvshows(API_URL_TV);
        })
        tagsEl.append(clear);
    }
    
}

getTvshows(API_URL_TV);

function getTvshows(url) {
  lastUrl = url;
    fetch(url).then(res => res.json()).then(data => {
        console.log(data.results)
        if(data.results.length !== 0){
            showTvShows(data.results);
            currentPage = data.page;
            nextPage = currentPage + 1;
            prevPage = currentPage - 1;
            totalPages = data.total_pages;

            current.innerText = currentPage;

            if(currentPage <= 1){
              prev.classList.add('disabled');
              next.classList.remove('disabled')
            }else if(currentPage>= totalPages){
              prev.classList.remove('disabled');
              next.classList.add('disabled')
            }else{
              prev.classList.remove('disabled');
              next.classList.remove('disabled')
            }

            tagsEl.scrollIntoView({behavior : 'smooth'})

        }else{
            main.innerHTML= `<h1 class="no-results">No Results Found</h1>`
        }
       
    })

}


function showTvShows(data) {
    main.innerHTML = '';

    data.forEach(tvshow => {
        const {name, poster_path, vote_average, overview, id} = tvshow;
        const tvEl = document.createElement('div');
        tvEl.classList.add('tvShow');
        tvEl.innerHTML = `
             <img src="${poster_path? IMG_URL+poster_path: "http://via.placeholder.com/1080x1580" }" alt="${name}">

            <div class="tv-info">
                <h3>${name}</h3>
                <span class="${getColor(vote_average)}">${vote_average}</span>
            </div>

            <div class="overview">

                <h3>Overview</h3>
				<p>
                ${overview}
				</p>
                <br/> 
                <button class="know-more" id="${id}">Know More</button> 
            	<input type="button" class="know-more" id="unmark-${id}" value="unmark Tvshow">
				<input type="button" class="know-more" id="mark-${id}" value="mark Tvshow">
				</div>`
		
		
		
        main.appendChild(tvEl);

		ifItExists(id, name);
		
		let markbutton = document.getElementById("mark-"+id);
		markbutton.addEventListener('click', () => {
			enterTvshow(tvshow);
		})
		
		let unmarkbutton = document.getElementById("unmark-"+id);
		unmarkbutton.addEventListener('click', () => {
			deleteTvshow(tvshow);
		})
		
        document.getElementById(id).addEventListener('click', () => {
          console.log(id)
          openNavTv(tvshow)
        })
    })
}

function enterTvshow(Tvshow){
	let tvshowObject = {
			"name": Tvshow.name,
			"rating": "How much would you rate the show?",
			"date": "",
			"season": 0,
			"episode":0
		}
		sessionStorage.setItem("tvshowobject",JSON.stringify(tvshowObject))
		window.location.href = 'http://localhost:8090/tvshowview2';
	
}

function deleteTvshow(Tvshow){
	fetch('http://localhost:8080/deleteTvshow',{
		method: "POST",
		headers:{
			"Content-Type": "application/json",
			'X-CSRF-TOKEN': token
		},
		body: JSON.stringify(Tvshow.name)
	}).then((response)=>{
		return response.text();
	}).then(data=>{
		window.location.href = 'http://localhost:8080'+data;
	})
}

function ifItExists(tvTmbdId, tvTmbdName){
	fetch('http://localhost:8080/getTvshowList')
		.then((response)=>{
				return response.json();
			})
			.then((data)=>{
				data.forEach(tvItem =>{
					console.log(tvItem);
					if(tvTmbdName === tvItem.name){
						document.getElementById("unmark-"+tvTmbdId).style.display = "";
						document.getElementById("mark-"+tvTmbdId).style.display = "none";
					}
					else{
						document.getElementById("mark-"+tvTmbdId).style.display = "";
						document.getElementById("unmark-"+tvTmbdId).style.display = "none";
					}
				})
				if(data.length == 0){
						document.getElementById("mark-"+tvTmbdId).style.display = "";
						document.getElementById("unmark-"+tvTmbdId).style.display = "none";
				}
			})				
}

const overlayContent = document.getElementById('overlay-content');
/* Open when someone clicks on the span element */
function openNavTv(tvshow) {
  let id = tvshow.id;
  fetch(BASE_URL_TV + '/tv/'+id+'/videos?'+API_KEY).then(res => res.json()).then(videoData => {
    console.log(videoData);
    if(videoData){
      document.getElementById("myNav").style.width = "100%";
      if(videoData.results.length > 0){
        var embed = [];
        var dots = [];
        videoData.results.forEach((video, idx) => {
          let {name, key, site} = video

          if(site == 'YouTube'){
              
            embed.push(`
              <iframe width="560" height="315" src="https://www.youtube.com/embed/${key}" title="${name}" class="embed hide" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
          
          `)

            dots.push(`
              <span class="dot">${idx + 1}</span>
            `)
          }
        })
        
        var content = `
        <h1 class="no-results">${tvshow.original_name}</h1>
        <br/>
        
        ${embed.join('')}
        <br/>

        <div class="dots">${dots.join('')}</div>
        
        `
        overlayContent.innerHTML = content;
        activeSlide=0;
        showVideos();
      }else{
        overlayContent.innerHTML = `<h1 class="no-results">No Results Found</h1>`
      }
    }
  })
}

/* Close when someone clicks on the "x" symbol inside the overlay */
function closeNav() {
  document.getElementById("myNav").style.width = "0%";
}

var activeSlide = 0;
var totalVideos = 0;

function showVideos(){
  let embedClasses = document.querySelectorAll('.embed');
  let dots = document.querySelectorAll('.dot');

  totalVideos = embedClasses.length; 
  embedClasses.forEach((embedTag, idx) => {
    if(activeSlide == idx){
      embedTag.classList.add('show')
      embedTag.classList.remove('hide')

    }else{
      embedTag.classList.add('hide');
      embedTag.classList.remove('show')
    }
  })

  dots.forEach((dot, indx) => {
    if(activeSlide == indx){
      dot.classList.add('active');
    }else{
      dot.classList.remove('active')
    }
  })
}

const leftArrow = document.getElementById('left-arrow')
const rightArrow = document.getElementById('right-arrow')

leftArrow.addEventListener('click', () => {
  if(activeSlide > 0){
    activeSlide--;
  }else{
    activeSlide = totalVideos -1;
  }

  showVideos()
})

rightArrow.addEventListener('click', () => {
  if(activeSlide < (totalVideos -1)){
    activeSlide++;
  }else{
    activeSlide = 0;
  }
  showVideos()
})


function getColor(vote) {
    if(vote>= 8){
        return 'green'
    }else if(vote >= 5){
        return "orange"
    }else{
        return 'red'
    }
}

form.addEventListener('submit', (e) => {
    e.preventDefault();

    const searchTerm = search.value;
    selectedGenre=[];
    setGenreTv();
    if(searchTerm) {
        getTvshows(searchURL_TV+'&query='+searchTerm)
    }else{
        getTvshows(API_URL_TV);
    }

})

prev.addEventListener('click', () => {
  if(prevPage > 0){
    pageCallTv(prevPage);
  }
})

next.addEventListener('click', () => {
  if(nextPage <= totalPages){
    pageCallTv(nextPage);
  }
})

function pageCallTv(page){
  let urlSplit = lastUrl.split('?');
  let queryParams = urlSplit[1].split('&');
  let key = queryParams[queryParams.length -1].split('=');
  if(key[0] != 'page'){
    let url = lastUrl + '&page='+page
    getTvshows(url);
  }else{
    key[1] = page.toString();
    let a = key.join('=');
    queryParams[queryParams.length -1] = a;
    let b = queryParams.join('&');
    let url = urlSplit[0] +'?'+ b
    getTvshows(url);
  }
}