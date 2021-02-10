var aud = $('audio')[0];
$('.play-pause').on('click', function() {
        if (aud.paused) {
        aud.play();
        $('.play-pause').removeClass('icon-play');
        $('.play-pause').addClass('icon-stop');
        }
         else {
         aud.pause();
         $('.play-pause').removeClass('icon-stop');
         $('.play-pause').addClass('icon-play');
         }
        })

        //Create a content on focus requirement 1.4.13 Content bij hover of focus

        var parent = document.getElementById('parent');

        parent.onmouseover = function() {
            document.getElementById('popup').style.display = 'block';
        }

        //hide when mouse hovers away (code to turn it on)

//        parent.onmouseout = function() {
//            document.getElementById('popup').style.display = 'none';
//        }

        parent.onfocus = function() {
            document.getElementById('popup').style.display = 'block';
        }

        parent.onblur = function() {
            document.getElementById('popup').style.display = 'none';
        }

        // hide when ESC is pressed (code to make ESC function)

//        document.addEventListener('keydown', (e) => {
//          if ((e.keyCode || e.which) === 27)
//               document.getElementById('popup').style.display = 'none';
//        });


