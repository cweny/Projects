$(document).ready(function() {
    $("#show").click(function() {
        $('#board').show();
    });
	//Initialize Game
    lastPlay = Math.floor(columnSize / 2);
    winningLines = getWl();
    shuffleCols = sCols(lastPlay);
    board = newBoard();
    redTurn = true;
    for (var i = 0; i < rowSize; i++) {
        var row = document.createElement("TR");
        row.className = "row";
        row.id = "row" + i;
        document.getElementById("board").appendChild(row);
        for (var k = 0; k < columnSize; k++) {
            var slot = document.createElement("TD");
            slot.id = k.toString();
            slot.className = "slot";
            slot.style.backgroundColor = "white";
            $(".row").last().append(slot);
        }
    }
    //Restart Game
    $("#restart").click(function() {
        board = newBoard();
        $('.slot').css("background-color", "white");
        redTurn = true;
        var rows = $(".row");
        for (var i = 0; i < rows.length; i++) {
            var slots = rows[i].childNodes;
            for (var k = 0; k < slots.length; k++) {
                slots[k].onmouseout = setWhite;
            }
        }
		var boardComb1= {
				four: [],
				three: [],
				two: [],
				one: []
			};
		var boardComb2= {
			four: [],
			three: [],
			two: [],
			one: []
		};
        $('#board').show();
        $('#player').text("Player Red Turn");
    });
    //UI. Takes care of taking the user;s play and updating the board combinations for the AI to use
    $('.slot').click(function() {
		var row = droppableColumn(this.id);
		if(row < 0) return;
		var col = this.id;
		if(row>0) {
			var before1u = getuCombinations(1, col, row-1);
			var before2u = getuCombinations(2, col, row-1);
		}
		var before1 = getCombinations(1, col, row);
		var before2 = getCombinations(2, col, row);
        if (drop(this, 1)) {
			var after = getCombinations(1, col, row);
			addCombinations(before1,before2,after,1);
			if(row>0) {
				updateUnder(before1u, before2u, col,row);
			}
            lastPlay = this.id;
            var winner = checkWin();
            if (winner !== 0) {
                $('#board').hide();
                if (winner === 1) {
                    $('#player').text("Player Red Won");
                    return;
                } else if (winner === 2) {
                    $('#player').text("Player Blue Won");
                    return;
                }
            }
            compMove();
            winner = checkWin();
            if (winner !== 0) {
                $('#board').hide();
                if (winner === 1)
                    $('#player').text("Player Red Won");
                else if (winner === 2)
                    $('#player').text("Player Blue Won");
            }
        }
    });
    $(".slot").hover(function() {
            $(this).css('cursor', 'pointer');
            var row = rowSize - 1;
            while (board[row][this.id] !== 0) {
                row--;
                if (row === -1) {
                    break;
                }
            }
            if (row !== -1) {
                var slots = $(".row")[row].childNodes;
                var x = slots[this.id];
                x.style.backgroundColor = "pink";
            }
        },
        function() {
            var row = rowSize - 1;
            while (board[row][this.id] !== 0) {
                row--;
                if (row === -1) {
                    break;
                }
            }
            if (row !== -1) {
                var slots = $(".row")[row].childNodes;
                var x = slots[this.id];
                x.style.backgroundColor = "white";
            }
		});
});