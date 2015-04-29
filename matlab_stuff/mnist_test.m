function [data] = mnist_test(imgFile, labelFile, readDigits, offset)
    %d = readMNIST('D:\ITU\algorithms_seminar\mnist\train-images.idx3-ubyte', 'D:\ITU\algorithms_seminar\mnist\train-labels.idx1-ubyte', 3,0);
    d = readMNIST(imgFile, labelFile, readDigits, offset);
    data = cell(readDigits);
    for i = 1:readDigits
        data{i} = d(:,:,i);
    end
    
    
    %data = d(:,:,:);
% disp(data(:,:,3));
     disp(data{1});

end