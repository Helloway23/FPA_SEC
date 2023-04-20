package com.HelloWay.HelloWay.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.HelloWay.HelloWay.entities.Board;
import com.HelloWay.HelloWay.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    BoardService boardService;

    @Autowired
    public BoardController( BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Board addNewBoard(@RequestBody Board board) {
        return boardService.addNewBoard(board);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Board> allBoards(){
        return boardService.findAllBoards();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Board findBoardById(@PathVariable("id") long id){
        return boardService.findBoardById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateBoard(@RequestBody Board board){
        boardService.updateBoard(board); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteBoard(@PathVariable("id") long id){
        boardService.deleteBoard(id); }

    @PostMapping("/add/id_zone/{id_zone}")
    @ResponseBody
    public Board addNewBoardByIdZone(@RequestBody Board board, @PathVariable Long id_zone) {
        return boardService.addBoardByIdZone(board, id_zone);
    }

    @GetMapping("/id_zone/{id_zone}")
    @ResponseBody
    public List<Board> getBoardsByIdZone( @PathVariable Long id_zone) {
        return boardService.getBoardsByIdZone( id_zone);
    }


}
