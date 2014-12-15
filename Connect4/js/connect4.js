		$(document).ready(function(){
			var MAX_DEPTH = 8;
			$("#show").click(function(){
				$('#board').show();
			});
			var columnSize = 7;
			var rowSize = 6;
			var sCols = function() {
				var shuffled = [];
				var start = Math.floor(columnSize/2);
				var t=1; 
				shuffled.push(start);
				while (t+start<columnSize || start-t>=0) {
					if(start-t>=0) {
						shuffled.push(start-t);
					}
					if(start+t<columnSize) {
						shuffled.push(start+t);
					}
					t++;
				}
				return shuffled;
			};
			var shuffleCols = sCols();
			console.log(shuffleCols);
			var newBoard = function(){
				var newB = [];
				
				for(var i=0; i<rowSize; i++){
					var temp = [];
					for(var n=0; n<columnSize; n++){
						temp[n] = 0;
					}
					newB[i]=temp;
				}
				return newB;
			};
			var getWl = function() {
				var wl = [];
				//-
				for(var r=0; r<rowSize; r++) {
					for(var c=0; c<columnSize-3;c++) {
						wl.push(
							[{row:r, col:c},
							{row:r, col:c+1},
							{row:r, col:c+2},
							{row:r, col:c+3}]
						);
					}
				}
				//|
				for(var c=0; c<columnSize; c++) {
					for(var r=0; r<rowSize-3;r++) {
						wl.push(
							[{row:r, col:c},
							{row:r+1, col:c},
							{row:r+2, col:c},
							{row:r+3, col:c}]
						);
					}
				}
				// /
				for(var r=0; r<rowSize-3; r++) {
					for(var c=0; c<columnSize-3;c++) {
						wl.push(
							[{row:r, col:c},
							{row:r+1, col:c+1},
							{row:r+2, col:c+2},
							{row:r+3, col:c+3}]
						);
					}
				}
				// \

				for(var r=0; r<rowSize-3; r++) {
					for(var c=3; c<columnSize;c++) {
						wl.push(
							[{row:r, col:c},
							{row:r+1, col:c-1},
							{row:r+2, col:c-2},
							{row:r+3, col:c-3}]
						);
					}
				}
				return wl;
			};
			var winningLines = getWl();
			var board = newBoard();
			var analyzeBoard = function(player) {
				var combs = {four:[],
							three:[],
							two:[],
							one:[]};
				var opponent = 0;
				if(player === 2) {
					opponent = 1;
				} else if(player === 1) {
					opponent = 2;
				}
				for(var i in winningLines) {
					var matches = 0;
					for(var k in winningLines[i]) {
						if(board[winningLines[i][k].row][winningLines[i][k].col] === player) {
							matches++;
						} else if(board[winningLines[i][k].row][winningLines[i][k].col] === opponent) {
							matches = 0;
							break;
						}
					}
					switch(matches) {
						case 4:
							combs.four.push(winningLines[i]);
							break;
						case 3:
							combs.three.push(winningLines[i]);
							break;
						case 2:
							combs.three.push(winningLines[i]);
							break;
						case 1:
							combs.three.push(winningLines[i]);
							break;
					}
				}
				return combs;
			};
			var droppableColumn = function(col) {
				var row = rowSize-1;
				while(board[row][col] !== 0) {
					row--;
					if(row === -1) {
						return row;
					}
				}
				return row;
			};
			var undoColumn = function(col) {
				var row = 0;
				while(board[row][col] === 0) {
					row++;
					if(row === rowSize) {
						console.log("error");
						return;
					}
				}
				
				board[row][col]=0;
			};
			var setRed = function(){
				this.style.backgroundColor = "red";
			};
			var setBlue = function(){
				this.style.backgroundColor = "blue";
			};
			var setWhite = function(){
				this.style.backgroundColor = "white";
			};
			var redTurn = true;
			var checkWin = function(){
				for(var row = 0; row<board.length; row++){
					for(var col = 0; col<board[0].length; col++){
						var player = board[row][col];
						if(player === 0){
							continue;
						}
						if (row+3 < board.length && board[row+1][col] === player && board[row+2][col] === player && board[row+3][col] === player) 
							return player;
						if (col+3 < board[0].length && board[row][col+1] === player && board[row][col+2] === player && board[row][col+3] === player) 
							return player;
						if ((row+3 < board.length && col+3 < board[0].length)
							&& board[row+1][col+1] === player && board[row+2][col+2] === player && board[row+3][col+3] === player) 
							return player;
						if ((row-3 > 0 && col+3 < board[0].length)
							&& board[row-1][col+1] === player && board[row-2][col+2] === player && board[row-3][col+3] === player) 
							return player;
					}
				}
				return 0;
			};			
			//Initialize game
			for(var i=0; i<rowSize; i++) {
				var row = document.createElement("TR");
				row.className = "row";
				row.id = "row"+i;
				document.getElementById("board").appendChild(row);
				for(var k=0; k<columnSize; k++) {
					var slot = document.createElement("TD");
					slot.id = k.toString();
					slot.className = "slot";
					slot.style.backgroundColor = "white";
					$(".row").last().append(slot);
				}
			}
			//$('#show').hide();
			//Restart Game
			$("#restart").click(function(){
				board = newBoard();
				$('.slot').css("background-color","white");
				redTurn = true;
				var rows = $(".row");
				for (var i=0; i<rows.length; i++){
					var slots = rows[i].childNodes;
					for(var k=0; k<slots.length; k++){
						slots[k].onmouseout = setWhite;
					}
				}
				$('#board').show();
				$('#player').text("Player Red Turn");
			});
			//Make a play
			var drop = function(element, player){
				var row = rowSize-1;
				while(board[row][element.id] !== 0) {
					row--;
					if(row === -1) {
						break;
					}
				}
				if(row !== -1) {
					if(redTurn){
						board[row][element.id]=1;
						$('#player').text("Player Blue Turn");
					}else{
						board[row][element.id]=2;
						$('#player').text("Player Red Turn");
					}
					var slots = $(".row")[row].childNodes;
					var x = slots[element.id];
					if(redTurn){
						x.style.backgroundColor = "red";
						x.onmouseout = setRed;
					}else{
						x.style.backgroundColor = "blue";
						x.onmouseout = setBlue;
					}
					
					redTurn = !redTurn;
				}
				if(row>0 && player===1) {
					row--;
					var slots = $(".row")[row].childNodes;
					var x = slots[element.id];
					x.style.backgroundColor = "pink";
				}
				if(row!==-1) {
					return true;
				} else {
					return false;
				}
			};
			//AI
			var evaluate = function(player) {
				return 0;
			};
			var negamax = function(depth, alpha, beta, player) {
				
				var opponent = 0;
				if(player === 2) {
					opponent = 1;
				} else if(player === 1) {
					opponent = 2;
				}
				var winner = checkWin();
				
				if(winner === player){
					return 10000-(MAX_DEPTH-depth);
				} else if(winner ===opponent) {
					return -10000+(MAX_DEPTH-depth);
				}
				
				if(depth === 0) {
					return evaluate(player);
				}
				var thisAlpha = alpha;
				var i;
				var m;
				for(m in shuffleCols) {
					i=shuffleCols[m];
					var row = droppableColumn(i);
					if(row >= 0) {
						board[row][i] = player;
					} else {
						continue;
					}
					/*if(checkWin()==player){
						undoColumn(i);
						var value = 10000;
						if(value >= beta) {
						return beta;
						}
						if(value>alpha) {
							thisAlpha = val;
						}
						continue;
					}*/ 
					var val = -negamax(depth-1, -beta, -thisAlpha, opponent);
					undoColumn(i);
					if(val >= beta) {
						return beta;
					}
					if(val>alpha) {
						thisAlpha = val;
					}
					//console.log(spaces+"player:" + player + "   depth:" + 1 +"   column:" +i+ "value:"  +val);
				}
				return thisAlpha;
			};
			var pickMove = function(player){
				var best = -10000;
				var bestCol = 0;
				var opponent = 0;
				
				if(player === 2) {
					opponent = 1;
				} else if(player === 1) {
					opponent = 2;
				}
				
				for(var i=0; i<columnSize; i++) {
					var row = droppableColumn(i);
					if(row >= 0) {
						board[row][i] = player;
					} else {
						if(bestCol===i){
							bestCol++;
						}
						continue;
					}
					
					var val = -negamax(MAX_DEPTH, -10000, 10000, opponent);
					
					undoColumn(i);
					
					if(val > best) {
						best = val;
						bestCol = i;
					}
					console.log("PICKMOVE -- player:"+player+" column:"+i+"\tdepth:"+2+"\t   value:"+val);
				}
				return bestCol;
			};
			var compMove = function(){
				var col = pickMove(2);
				var row = droppableColumn(col);
				var slots = $(".row")[row].childNodes;
				var element = slots[col];
				drop(element,2);
			};
			//UI
			$('.slot').click(function(){
				if(drop(this,1)) {
					var winner = checkWin();
					if(winner!==0){
						$('#board').hide();
						if(winner === 1){
							$('#player').text("Player Red Won");
							return;
						} else if(winner === 2){
							$('#player').text("Player Blue Won");
							return;
						}
					}
					compMove();
					winner = checkWin();
					if(winner!==0){
						$('#board').hide();
						if(winner === 1)
							$('#player').text("Player Red Won");
						else if(winner === 2)
							$('#player').text("Player Blue Won");
					}
				console.log(board);
				}
			});
			$(".slot").hover(function(){
				$(this).css('cursor','pointer');
				var row = rowSize-1;
				while(board[row][this.id] !== 0) {
					row--;
					if(row === -1) {
						break;
					}
				}
				if(row!==-1) {
					var slots = $(".row")[row].childNodes;
					var x = slots[this.id];
					x.style.backgroundColor = "pink";
				}
			  },
			  function(){
				var row = rowSize-1;
				while(board[row][this.id] !== 0) {
					row--;
					if(row === -1) {
						break;
					}
				}
				if(row!==-1) {
					var slots = $(".row")[row].childNodes;
					var x = slots[this.id];
					x.style.backgroundColor = "white";
				}
			});
		});