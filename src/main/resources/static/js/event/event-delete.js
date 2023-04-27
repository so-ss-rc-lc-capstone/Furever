function confirmDelete() {
    if (confirm("Are you sure you want to delete this event?")) {
        return true;
    } else {
        return false;
    }
}