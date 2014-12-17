$(document).ready(function() {
    $("#show").click(function() {
        $('#board').show();
    });

    lastPlay = Math.floor(columnSize / 2);
    winningLines = getWl();
    shuffleCols = sCols(lastPlay);
    board = newBoard();
    redTurn = true;

    //Initialize game
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
        $('#board').show();
        $('#player').text("Player Red Turn");
    });
    //UI
	var element = [];
    $('.slot').click(function() {
        if (drop(this, 1)) {
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
		//console.log(board);
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