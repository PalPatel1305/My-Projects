from django.shortcuts import render,redirect
from django.http import HttpResponse
from .models import INFO,skill,experince,edu,DP,project
# from .form import form1
from .forms import form
from django.core.mail import EmailMessage
from project.settings import EMAIL_HOST_USER

# Create your views here.
def home(request):
    if request.POST:
        name = request.POST.get('name')
        email = request.POST.get('_replyto')
        subject = request.POST.get('Subject')
        msg = request.POST.get('message')
        
        email_c = 'My Mail id is {}'.format(email)
        main_msg = email_c + msg
        email = EmailMessage(subject, main_msg, to=[email])
        email.send()
        return redirect('/')



    data = INFO.objects.first()
    skills = skill.objects.all()
    exp = experince.objects.all()
    ed = edu.objects.all()
    dp = DP.objects.last()
    pro = project.objects.all()
    context={
        'data':data,
        'skills':skills,
        'exp':exp,
        'ed':ed,
        'dp':dp,
        'pro':pro
        
    }
    return render(request,"app/home.html",context)
    