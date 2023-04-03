package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class DefaultController {
    private final ArrayList<Task> TASKS = new ArrayList<>();
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String index() {
        return (LocalDateTime.now()).toString();
    }

    @PostMapping("/tasks/")
    public ResponseEntity<Task> newTask(String name, String description) {
        Task task = new Task();
        task.setTitle(name);
        task.setDescription(description);
        task.setCreationTime(LocalDateTime.now());
        task.setDone(true);
        Task newTask = taskRepository.save(task);
        return ResponseEntity.ok(newTask);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity showTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/tasks/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ArrayList<Task> showTasks() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : taskIterable) {
            taskList.add(task);
        }
        return taskList;
    }

    @PatchMapping("/tasks/{id}")
    protected ResponseEntity updateTask(@PathVariable int id,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) boolean done) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);
        }
        Task task = optionalTask.get();
        if (name != null) {
            task.setTitle(name);
        }
        if (description != null) {
            task.setDescription(description);
        }
        task.setCreationTime(LocalDateTime.now());
        task.setDone(done);
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }
}
