$(document).ready(function() {
    $(".treeSelectTable").treeTable(
    {
      initialState: "expanded"
    });
    
    // Drag & Drop Example Code
    $(".treeSelectTable .node, .treeSelectTable .parentNode").draggable({
      helper: "clone",
      opacity: .75,
      refreshPositions: true, // Performance?
      revert: "invalid",
      revertDuration: 300,
      scroll: true
    });
    
    $(".treeSelectTable .parentNode").each(function() {
      $($(this).parents("tr")[0]).droppable({
        accept: ".node, .parentNode",
        drop: function(e, ui) { 
          $($(ui.draggable).parents("tr")[0]).appendBranchTo(this);
        },
        hoverClass: "accept",
        over: function(e, ui) {
          if(this.id != $(ui.draggable.parents("tr")[0]).id && !$(this).is(".expanded")) {
            $(this).expand();
          }
        }
      });
    });
    
    // Make visible that a row is clicked
    $("table.treeSelectTable tbody tr").mousedown(function() {
      $("tr.selected").removeClass("selected"); // Deselect currently selected rows
      $(this).addClass("selected");
    });
    
    // Make sure row is selected when span is clicked
    $("table.treeSelectTable tbody tr span").mousedown(function() {
      $($(this).parents("tr")[0]).trigger("mousedown");
    });
  });
  