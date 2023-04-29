function confirmDelete() {
    if (confirm("Are you sure you want to delete your profile?\n" +
        "All details connected to this user will be wiped!")) {
        return true;
    } else {
        return false;
    }
}
